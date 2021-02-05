package de.zero.ebox.eboxbackend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 {
 "nid": "173893",
 "uid": "1",
 "name": "admin",
 "title": "mirella kroes-the boom room 284-sat-16-11-2019",
 "replacetitle": "",
 "status": "1",
 "created": "1574021206",
 "changed": "1574093964",
 "setcreated": "1573945200",
 "lastheard": "15000000",
 "duration": 3136,
 "taxonomy": {
 "167": {
 "tid": "167",
 "vid": "11",
 "name": "set",
 "description": "",
 "weight": "0",
 "v_weight_unused": "0"
 }
 },
 "votes": {
 "my": null,
 "avg": 5,
 "count": "1",
 "firstgoodvote": {
 "uid": "1",
 "name": "admin",
 "vote": "5",
 "timestamp": "15000000"
 },
 "all": [
 {
 "uid": "1",
 "name": "admin",
 "vote": "5",
 "timestamp": "15000000"
 }
 ],
 "sum": 1
 },
 "bookmarked": true,
 "trackinfo": [
 {
 "filename": "01-mirella_kroes-the_boom_room_284-sat-16-11-2019-1king.mp3",
 "downloadfilename": "mirella kroes-the boom room 284-sat-16-11-2019 2019-11-17.mp3",
 "fsize": "111033107",
 "duration": "3136",
 "frequency": "48000",
 "bitrate": "283",
 "mode": "joint stereo",
 "artist": "",
 "title": "mirella kroes-the boom room 284-sat-16-11-2019"
 }
 ],
 "comments": [],
 "artists": [],
 "myplaycount": 1,
 "allplaycount": 1,
 "djset": null
 }
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MischungxlNode {

    private int id;

}
