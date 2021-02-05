package de.zero.ebox.eboxbackend.data;

import de.zero.ebox.eboxbackend.model.JsonSetNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.core.FetchSpec;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class MischungxlRepositoryImpl implements MischungxlRepositoryCustom {

    @Autowired
    private DatabaseClient client;



    @Override
    public Flux<JsonSetNode> getMischungxlNodes() {
        return Flux.empty();
    }

    @Override
    public Mono<JsonSetNode> getMischungxlNode(int nid) {
        JsonSetNodeMapper mapper = new JsonSetNodeMapper();
        Mono<JsonSetNode> result = client.sql("SELECT * FROM node n JOIN mischungxl_nodes mxl using(nid) WHERE n.nid = :nid")
              .bind("nid", nid)
              .map(mapper)
              .first();
        return result;
    }


    /*
function listsets_search_create_query($sp, $noderestriction = NULL, $onlyactive = TRUE) {
	global $user;
	$parameter = array();
	$select = "SELECT a.nid ";
	if ($sp->mode['value'] == FILTER_13_ZU_LOESCHENDE_SETS) {
		$select .= ", a.delorder ";
	}
	if ($sp->score['value']) {
		$select .= ", a.score ";
	}
	$select .= " FROM";

	$select .= "(SELECT n.nid, n.created, n.setcreated, n.duration, lh.lastheard ";
	if ($sp->mode['value'] == FILTER_12_NEU_GUT_BEWERTETE_SETS) {
		$select .= ", firstgoodvotes.timestamp as firstgoodvotestimestamp ";
	}
	if ($sp->mode['value'] == FILTER_13_ZU_LOESCHENDE_SETS) {
		$select .= ", to_backup.delorder ";
	}
	if ($sp->mode['value'] == FILTER_04_MEINE_BEST_OF) {
		$select .= ", my.timestamp as mytimestamp ";
	}
	if ($sp->score['value']) {
		$select .= ", (select userscore FROM mxl_get_score_data(n.nid,%d)) as score ";
		$parameter[] = $user->uid;
	}
	if (isset($sp->entriesperartist['value']) && $sp->entriesperartist['value'] > 0) {
		$select .= ", row_number() over (PARTITION BY n.artistnids[1] ORDER BY (SELECT userscore
							FROM mxl_get_score_data(n.nid, %d)) DESC) as artistindex ";
		$parameter[] = $user->uid;
	}

	$from = "FROM view_mischungxl_nodes n \n";
	if ($sp->score['value']) {
		$from .= "\tJOIN mischungxl_scores score ON (n.nid=score.nid AND score.uid=%d) \n";
		$parameter[] = $user->uid;
	}

	if ($sp->bookmarked['value']) {
		$from .= "\tJOIN mischungxl_bookmarks b ON (n.nid=b.nid AND b.uid=%d) \n";
		$parameter[] = $user->uid;
	}

	$from .= "\tLEFT JOIN users u ON (n.uid=u.uid) \n";
	$from .= "\tLEFT JOIN nodevote my ON(my.uid=%d AND n.nid=my.nid)\n";
	$parameter[] = $user->uid;

	$from .= "\tLEFT JOIN ( \n";
	$from .= "\t\tselect nid, max(timestamp) as lastheard from mischungxl_listening where uid=%d group by nid \n";
	$from .= "\t) lh ON(n.nid = lh.nid)\n";
	$parameter[] = $user->uid;

	if ($sp->mode['value'] == FILTER_12_NEU_GUT_BEWERTETE_SETS) {
		$from .= "JOIN (SELECT nid,vote,uid,timestamp FROM "
					. "(SELECT n.nid,n.vote,n.uid, n.timestamp, row_number() over"
					. " (PARTITION BY n.nid ORDER BY n.timestamp) as row FROM"
					. " mischungxl_nodes mn JOIN nodevote n ON n.nid=mn.nid WHERE n.vote >3) "
					. "votes WHERE votes.row=1) firstgoodvotes ON firstgoodvotes.nid = n.nid\n ";
	}

	if ($sp->mode['value'] == FILTER_13_ZU_LOESCHENDE_SETS) {
		$from .= "JOIN (SELECT nid,row_number() over (PARTITION BY
        null) as delorder FROM
         view_mischungxl_nodes_to_delete) to_backup ON
         to_backup.nid = n.nid\n ";
	}

	$where = "WHERE TRUE ";

	if ($sp->titlesearch['value']) {
		if (preg_match('/^\/(.*)\/$/', $sp->titlesearch['value'], $matches) === 0) {
			$where .= "AND \n(title ILIKE '%%%s%%' OR fulltitle ILIKE '%%%s%%'
      OR n.artistnids && (
        SELECT array_agg(an.nid)
          FROM mischungxl_artist_nodes an
          JOIN node n ON an.nid=n.nid
        WHERE
          n.title ILIKE '%%%s%%'
          OR array_to_string(an.aliases,' ') ILIKE '%%%s%%'
          )
        ) ";

			$parameter[] = str_replace(array(' ', '*', '%'), '%%', $sp->titlesearch['value']);
			$parameter[] = str_replace(array(' ', '*', '%'), '%%', $sp->titlesearch['value']);
			$parameter[] = str_replace(array(' ', '*', '%'), '%%', $sp->titlesearch['value']);
			$parameter[] = str_replace(array(' ', '*', '%'), '%%', $sp->titlesearch['value']);
		}
		else {
			$where .= "AND \n(title ~* '%s' OR fulltitle ~* '%s'
      OR n.artistnids && (
        SELECT array_agg(an.nid)
          FROM mischungxl_artist_nodes an
          JOIN node n ON an.nid=n.nid
        WHERE
          n.title ~* '%s'
          OR array_to_string(an.aliases,' ') ~* '%s'
          )

      ) ";
			$parameter[] = $matches[1];
			$parameter[] = $matches[1];
			$parameter[] = $matches[1];
			$parameter[] = $matches[1];
		}
	}

	if (is_array($sp->myvotes['value']) && sizeof($sp->myvotes['value']) > 0) {
		$parts = array();
		// TODO: where sowieso in .....
		foreach ($sp->myvotes['value'] as $mv) {
			switch ($mv) {
				case 0:
					$parts[] = "my.vote IS NULL";
					break;
				case 1:
				case 2:
				case 3:
				case 4:
				case 5:
					$parts[] = "my.vote = %d";
					$parameter[] = $mv;
					break;
				default:
					break;
			}
		}
		$clause = implode(' OR ', $parts);
		$where .= "AND \n($clause) ";
	}

	if ($sp->exclude_titlesearch['value']) {
		if (preg_match('/^\/(.*)\/$/', $sp->exclude_titlesearch['value'], $matches) === 0) {
			$where .= "AND \nNOT (title ILIKE '%%%s%%' OR fulltitle ILIKE '%%%s%%') ";
			$parameter[] = str_replace(array(
					' ',
					'*',
					'%',
			), '%%', $sp->exclude_titlesearch['value']);
			$parameter[] = str_replace(array(
					' ',
					'*',
					'%',
			), '%%', $sp->exclude_titlesearch['value']);
		}
		else {
			$where .= "AND \nNOT (title ~* '%s' OR fulltitle ~* '%s') ";
			$parameter[] = $matches[1];
			$parameter[] = $matches[1];
		}
	}

	$res = db_query("SELECT array_agg(DISTINCT tid) AS tids FROM term_data WHERE vid=%d",
			MxlSettings::getMischungxlTaxonomyVidGenres());
	$tids_as_obj = db_fetch_object($res);
	$genretids = pg_array_parse($tids_as_obj->tids);

	$res = db_query("SELECT array_agg(DISTINCT tid) AS tids FROM term_data WHERE vid=%d",
			MxlSettings::getMischungxlTaxonomyVidTags());
	$tids_as_obj = db_fetch_object($res);
	$tagtids = pg_array_parse($tids_as_obj->tids);

	if (is_array($sp->getIncludeTids())) {
		$p_aGenre = array_intersect(array_filter($sp->getIncludeTids()), $genretids);
		$p_aTags = array_intersect(array_filter($sp->getIncludeTids()), $tagtids);
		if (sizeof($p_aGenre) > 0) {
			$where .= "AND " . ($sp->include_tags_exclude['value'] ? "NOT " : "") . "(\n ";
			$first = TRUE;
			foreach ($p_aGenre as $tid) {
				if ($first == FALSE) {
					$where .= "OR ";
				}
				$where .= " \ntids && (SELECT mxl_get_tree_terms(%d)) ";
				$parameter[] = $tid;
				$first = FALSE;
			}
			$where .= ")\n";
		}
		if (sizeof($p_aTags) > 0) {
			$where .= "AND " . ($sp->include_tags_exclude['value'] ? "NOT " : "") . "(\n ";
			$first = TRUE;
			foreach ($p_aTags as $tid) {
				if ($first == FALSE) {
					$where .= "OR ";
				}
				$where .= " \ntids && (SELECT mxl_get_tree_terms(%d)) ";
				$parameter[] = $tid;
				$first = FALSE;
			}
			$where .= ")\n";
		}
	}

	if (is_array($sp->getExcludeTids())) {
		$p_aGenre = array_filter($sp->getExcludeTids());
		if (sizeof($p_aGenre) > 0) {
			$where .= "AND NOT (\n ";
			$first = TRUE;
			foreach ($p_aGenre as $tid) {
				if ($first == FALSE) {
					$where .= "OR ";
				}
				$where .= " \ntids && (SELECT mxl_get_tree_terms(%d)) ";
				$parameter[] = $tid;
				$first = FALSE;
			}
			$where .= ")\n";
		}
	}

	if ($sp->uploaders['value']) {
		$f_uploader_str = implode(",", $sp->uploaders['value']);
		$where .= "AND \nu.uid = ANY (ARRAY[%s]) ";
		$parameter[] = $f_uploader_str;
	}

	if ($sp->artistnids['value']) {
		$f_djs_str = implode(",", $sp->artistnids['value']);
		$where .= "AND \nn.artistnids && ARRAY[%s] ";
		$parameter[] = $f_djs_str;
	}

	if ($noderestriction != NULL && is_array($noderestriction) && sizeof($noderestriction) > 0) {
		$l_sNoderestriction = implode(",", $noderestriction);
		$where .= "AND \nn.nid = ANY (ARRAY[%s]) ";
		$parameter[] = $l_sNoderestriction;
	}

	switch ($sp->mode['value']) {
		case FILTER_00_ALLE_SETS:
			break;
		case FILTER_01:
			$where .= " AND (votecount = 0)";
			break;
		case FILTER_02:
			$where .= " AND (NOT my.nid IS NULL)";
			break;
		case FILTER_03:
			$where .= " AND (my.nid IS NULL AND votecount > 0)";
			break;
		case FILTER_04_MEINE_BEST_OF:
			$where .= " AND (my.vote = 5)";
			break;
		case FILTER_05_FREMDE_BEST_OF:
			$where .= " AND (my.nid IS NULL AND avgvote >= 4)";
			break;
		case FILTER_06_MEINE_DJ_FAVORITEN:
			$where .= " AND (my.nid IS NULL AND n.artistnids && array(SELECT
                                          djs.artistnid
                                        FROM
                                            (SELECT * FROM view_mischungxl_djrating WHERE uid =%d
                                              ) djs
                                        GROUP BY djs.artistnid
                                        HAVING sum(djs.rating) > 0 AND count(djs.vote) >= 2
                                        ))";
			$parameter[] = $user->uid;
			break;
		case FILTER_07_MEINE_GOLDMINE:
			$where .= " AND (my.nid IS NULL AND (n.artistnids is null OR array_length(n.artistnids,
                                 1)=0 OR NOT n.artistnids && ARRAY
                                       (SELECT
                                          djs.artistnid
                                        FROM
                                            (SELECT * FROM view_mischungxl_djrating WHERE uid =%d
                                              ) djs
                                        GROUP BY djs.artistnid
                                        HAVING sum(djs.rating) < 0 AND count(djs.vote) >= 2
                                        )))";
			$parameter[] = $user->uid;
			break;
		case FILTER_08_MANITOBA_DJ_FAVORITEN:
			$where .= " AND (my.nid IS NULL AND n.artistnids && array(SELECT
                                          djs.artistnid
                                        FROM
                                            (SELECT * FROM view_mischungxl_djrating) djs
                                        GROUP BY djs.artistnid
                                        HAVING sum(djs.rating) > 0 AND count(djs.vote) >= 2
                                        ))";
			break;
		case FILTER_09_MANITOBA_GOLDMINE:
			$where .= " AND (my.nid IS NULL AND (n.artistnids is null OR array_length(n.artistnids,
                                 1)=0 OR  NOT n.artistnids && ARRAY
                                       (SELECT
                                          djs.artistnid
                                        FROM
                                            (SELECT * FROM view_mischungxl_djrating) djs
                                        GROUP BY djs.artistnid
                                        HAVING sum(djs.rating) < 0 AND count(djs.vote) >= 2
                                        )))";
			break;
		case FILTER_10:
			break;
		case FILTER_11_UNBEWERTETE_DJS:
			$where .= " AND (my.nid IS NULL AND (n.artistnids is null OR
      array_length(n.artistnids,
                                 1)=0 OR  NOT n.artistnids && ARRAY
                                       (SELECT
                                          djs.artistnid
                                        FROM
                                            (SELECT * FROM view_mischungxl_djrating WHERE uid =%d
                                              ) djs
                                        GROUP BY djs.artistnid
                                        HAVING sum(djs.rating) != 0 AND count(djs.vote) >= 2
                                        )))";
			$parameter[] = $user->uid;
			break;
		case FILTER_12_NEU_GUT_BEWERTETE_SETS:
		case FILTER_13_ZU_LOESCHENDE_SETS:
			break;
	}
	if ($onlyactive) {
		$where .= " AND n.status=1";
	}

	if ($sp->lastheard['value']) {
		$where .= " AND NOT lh.lastheard IS NULL ";
	}

	if ($sp->notheard['value']) {
		$where .= " AND lh.lastheard IS NULL ";
	}

	if ($sp->notvoted['value']) {
		$where .= " AND my.nid IS NULL ";
	}

	if (isset($sp->ignore_entries_from_artists_voted_last_x_days['value'])
		 && $sp->ignore_entries_from_artists_voted_last_x_days['value'] > 0) {
		$where .= "	AND n.nid NOT IN (SELECT distinct ndel.nid"
					 . " FROM public.nodevote nv"
					 . " JOIN mischungxl_nodes n ON n.nid=nv.nid"
					 . " JOIN mischungxl_nodes ndel ON ndel.artistnids && n.artistnids"
					 . " WHERE nv.uid = %d AND nv.timestamp > extract(EPOCH FROM (now() - INTERVAL '%d DAY')))";
		$parameter[] = $user->uid;
		$parameter[] = $sp->ignore_entries_from_artists_voted_last_x_days['value'];
	}

	if (isset($sp->ignore_entries_from_artists_listened_last_x_days['value'])
		 && $sp->ignore_entries_from_artists_listened_last_x_days['value'] > 0) {
		$where .= "	AND n.nid NOT IN (SELECT distinct ndel.nid"
					 . " FROM public.mischungxl_listening nl"
					 . " JOIN mischungxl_nodes n ON n.nid=nl.nid"
					 . " JOIN mischungxl_nodes ndel ON ndel.artistnids && n.artistnids"
					 . " WHERE nl.uid = %d AND nl.timestamp > extract(EPOCH FROM (now() - INTERVAL '%d DAY')))";
		$parameter[] = $user->uid;
		$parameter[] = $sp->ignore_entries_from_artists_listened_last_x_days['value'];
	}

	$where .= ') a';
	if (isset($sp->entriesperartist['value']) && $sp->entriesperartist['value'] > 0) {
		$where .= ' WHERE a.artistindex <= %d';
		$parameter[] = $sp->entriesperartist['value'];
	}
	$query_array["select"] = $select;
	$query_array["from"] = $from;
	$query_array["where"] = $where;
	$query_array["parameter"] = $parameter;

	return $query_array;
}
     */
}

