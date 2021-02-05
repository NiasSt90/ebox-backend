package de.zero.ebox.eboxbackend.model;

import de.zero.ebox.eboxbackend.model.*;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SetNodeFactory {
    public static JsonSetNode createNode173893() {
        JsonGenre genreSet = JsonGenre.builder().tid(167).vid(11).name("set").description("").weight("0").v_weight_unused("0").build();
        JsonGenre genreTrance = JsonGenre.builder().tid(24).vid(2).name("trance").description("term for a genre of mischungxl (Trance)").weight("23").v_weight_unused("0").build();
        JsonTrackinfo trackinfo = JsonTrackinfo.builder()
                .filename("Aly and Fila Future Sound Of Egypt 580 09 JAN 2019.mp3")
                .downloadFilename("global-sets.com - aly and fila future sound of egypt 580 09 jan 2019 2019-01-10.mp3")
                .filesize(283325314)
                .duration(7083)
                .frequency(44100)
                .bitrate(Short.valueOf("320"))
                .mp3Mode("stereo")
                .artist("aly and fila")
                .title("Global-Sets.com - aly and fila Future Sound Of Egypt 580 09 JAN 2019")
                .build();

        return JsonSetNode.builder()
                .nid(173893)
                .uid(1)
                .userName("admin")
                .title("Global-Sets.com - aly and fila Future Sound Of Egypt 580 09 JAN 2019")
                .replaceTitle("Global-Sets.com - ${108165} Future Sound Of Egypt 580 09 JAN 2019")
                .status(1)
                .created(Instant.ofEpochSecond(1547119281))
                .modified(Instant.ofEpochSecond(1547119442))
                .setcreated(Instant.ofEpochSecond(1547074800))
                .lastheared(null)
                .duration(7083)
                .genre(Map.of(167, genreSet, 24, genreTrance))
                .vote(JsonVote.builder().myVote(null).average(null).count(0).firstGoodVote(null).voteSum(0).votesList(Collections.emptyList()).build())
                .bookmarked(false)
                .trackinfos(List.of(trackinfo))
                .comments(Collections.emptyList())
                .artists(List.of(new JsonArtistID(55102, 108165)))
                .myPlaycount(0)
                .allPlaycount(0)
                //.djset ??
                .dj(JsonDJ.builder().id(55102).name("aly and fila").country("").lastfm_artistinfo_timestamp(Instant.ofEpochSecond(1571513061)).build())
                .build();
    }

    public static JsonSetNode createNode178903() {
        JsonGenre deep_house = JsonGenre.builder().name("deep house").tid(123).vid(2).description("").weight("0").v_weight_unused("0").build();
        JsonGenre genreSet = JsonGenre.builder().name("set").tid(167).vid(11).description("").weight("0").v_weight_unused("0").build();
        JsonGenre electro_house = JsonGenre.builder().name("electro house").tid(155).vid(2).description("").weight("1").v_weight_unused("0").build();
        JsonGenre progressive_house = JsonGenre.builder().name("progressive house").tid(152).vid(2).description("").weight("2").v_weight_unused("0").build();
        JsonGenre techhouse = JsonGenre.builder().name("tech-house").tid(117).vid(2).description("").weight("3").v_weight_unused("0").build();
        JsonGenre vocal_house = JsonGenre.builder().name("vocal house").tid(156).vid(2).description("").weight("4").v_weight_unused("0").build();
        JsonGenre house = JsonGenre.builder().name("house").tid(20).vid(2).description("term for a genre of mischungxl (House)").weight("11").v_weight_unused("0").build();
        JsonGenre progressive = JsonGenre.builder().name("progressive").tid(115).vid(2).description("").weight("16").v_weight_unused("0").build();
        Map<Integer, JsonGenre> genresMap = Stream.of(deep_house, genreSet, electro_house, progressive_house, techhouse, vocal_house, house, progressive)
                .collect(Collectors.toMap(JsonGenre::getTid, g -> g));



        JsonTrackinfo trackinfo = JsonTrackinfo.builder()
                .filename("John Digweed - Transitions 769 (with Fort Romeau) - 24-MAY-2019.mp3")
                .downloadFilename("global-sets.com - john digweed - transitions 769 (with fort romeau) - 24-may-2019 2019-05-29.mp3")
                .filesize(283627920)
                .duration(7090)
                .frequency(48000)
                .bitrate(Short.valueOf("320"))
                .mp3Mode("stereo")
                .artist("")
                .title("Global-Sets.com - John Digweed - Transitions 769 (with Fort Romeau) - 24-MAY-2019")
                .build();

        JsonUserVote vote1 = new JsonUserVote(56, "pinki", 5, Instant.ofEpochSecond(1563552496));
        JsonUserVote vote2 = new JsonUserVote(43, "Nias", 5, Instant.ofEpochSecond(1570892042));
        JsonVote votes = JsonVote.builder().myVote(null).average(5.0).count(2)
                .firstGoodVote(vote1)
                .voteSum(2).votesList(List.of(vote1, vote2)).build();

        return JsonSetNode.builder()
                .nid(178903)
                .uid(1)
                .userName("admin")
                .title("Global-Sets.com - john digweed - Transitions 769 (with fort romeau) - 24-MAY-2019")
                .replaceTitle("Global-Sets.com - ${124341} - Transitions 769 (with ${101959}) - 24-MAY-2019")
                .status(1)
                .created(Instant.ofEpochSecond(1559146573))
                .modified(Instant.ofEpochSecond(1570892162))
                .setcreated(Instant.ofEpochSecond(1559080800))
                .lastheared(null)
                .duration(7090)
                .genre(genresMap)
                .vote(votes)
                .bookmarked(false)
                .trackinfos(List.of(trackinfo))
                .comments(Collections.emptyList())
                .artists(List.of(new JsonArtistID(63399, 101959), new JsonArtistID(35030, 124341)))
                .myPlaycount(0)
                .allPlaycount(7)
                //.djset ??
                .dj(JsonDJ.builder().id(63399).name("fort romeau").country("").lastfm_artistinfo_timestamp(Instant.ofEpochSecond(1569514934)).build())
                .build();

    }
}
