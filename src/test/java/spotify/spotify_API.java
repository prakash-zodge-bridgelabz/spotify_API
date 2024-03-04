package spotify;

import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class spotify_API {
    String token = "BQCX_dvHo8NuRmzLx9PKADf0_dI1FpXttJbwbvPKsJO66Jvk38r2hBg6ifnEVBIWrY-mDj7Zd-4XtVl3PvXo7xdJYREB44q52DzWSEzl3KTYUwPP65UZgMTSXyL_Ou4ko9zQYgK8WrLFt_8vxx31InxBd0ERo9cR605lFzh3X8oy0btfvczdclEs3m0m8CLD6pBUseF-uWD0Yejf3IXWwWRHPAS4AOgWVW5TUoTHHUPFMR4bVwWV4yDDpCJ-9YRxyMGqihMK0XddAhChvYqh8JYTzgVD9Q";
    String userID;
    @Test
    public void getUser(){
        Response res = given()
                .header("accept","application/json")
                .header("Content-Type","application/json")
                .header("Authorization","Bearer "+token)
                .when()
                .get("https://api.spotify.com/v1/me");
        res.prettyPrint();
        userID = res.path("id");
        res.then().assertThat().statusCode(200);
        System.out.println("user ID : "+userID);
    }

    @Test
    public void createPlaylist(){
        Response res = given()
                .header("accept","application/json")
                .header("Content-Type","application/json")
                .header("Authorization","Bearer "+token)
                .body("{\n" +
                        "    \"name\": \"New Dynamic Playlist by IDE\",\n" +
                        "    \"description\": \"New playlist description of IDE\",\n" +
                        "    \"public\": false\n" +
                        "}")
                .when()
                .post("https://api.spotify.com/v1/users/"+userID+"/playlists");
        res.prettyPrint();
        res.then().assertThat().statusCode(200);
    }
    //Users --> follow playlist
    @Test
    public void put_FollowPlaylist(){
        Response res = given()
                .header("accept","application/json")
                .header("Content-Type","application/json")
                .header("Authorization","Bearer "+token)
                .body("{\n" +
                        "    \"public\": false\n" +
                        "}")
                .when()
                .put("https://api.spotify.com/v1/playlists/5PCv6afEatU3z9cq2fBPDs/followers");
        res.prettyPrint();
        res.then().assertThat().statusCode(200);
    }
    //Users --> Unfollow Playlist
    @Test
    public void delete_UnfollowPlaylist(){
        Response res = given()
                .header("accept","application/json")
                .header("Content-Type","application/json")
                .header("Authorization","Bearer "+token)
                .when()
                .delete("https://api.spotify.com/v1/playlists/5PCv6afEatU3z9cq2fBPDs/followers");
        res.prettyPrint();
        res.then().assertThat().statusCode(200);
    }
    //Users --> Follow Artists or Users     //Error
    @Test
    public void put_FollowArtistsOrUsers(){
        Response res = given()
                .header("accept","application/json")
                .header("Content-Type","application/json")
                .header("Authorization","Bearer "+token)
                .body("{\n" +
                        "    \"ids\": [\n" +
                        "        \"string\"\n" +
                        "    ]\n" +
                        "}")
                .when()
                .put("https://api.spotify.com/v1/me/following?type=artist&ids=4pwXiI7Z5ZStkgKowZyoKi");
        res.prettyPrint();
        res.then().assertThat().statusCode(204);
    }

    //Playlist -> get playlist
    @Test
    public void get_GetPlaylist(){
        Response res = given()
                .header("accept","application/json")
                .header("Content-Type","application/json")
                .header("Authorization","Bearer "+token)
                .when()
                .get("https://api.spotify.com/v1/playlists/3cEYpjA9oz9GiPac4AsH4n");
        res.prettyPrint();
        res.then().assertThat().statusCode(200);
    }
    // Playlist - Change Playlist Details
    @Test
    public void put_ChangePlaylistDetails(){
        Response res = given()
                .header("accept","application/json")
                .header("Content-Type","application/json")
                .header("Authorization","Bearer "+token)
                .body("{\n" +
                        "    \"name\": \"Updated Playlist Name\",\n" +
                        "    \"description\": \"Updated playlist description\",\n" +
                        "    \"public\": false\n" +
                        "}")
                .when()
                .put("https://api.spotify.com/v1/playlists/7FNnHCoWKCkYM5gyoVNU9h");
        res.prettyPrint();
        res.then().assertThat().statusCode(200);
    }
    //Playlist --> Add Items to Playlist
    @Test
    public void post_AddItemsToPlaylist(){
        Response res = given()
                .header("accept","application/json")
                .header("Content-Type","application/json")
                .header("Authorization","Bearer "+token)
                .body("{\n" +
                        "    \"uris\": [\n" +
                        "        \"spotify:track:1BpvuKIG7jK5EiTp2tv5z8\"\n" +
                        "    ],\n" +
                        "    \"position\": 0\n" +
                        "}")
                .when()
                .post("https://api.spotify.com/v1/playlists/7FNnHCoWKCkYM5gyoVNU9h/tracks");
        res.prettyPrint();
        //     "snapshot_id": "AAAABqop77ZpOIaHBJrPVqe1l1ljrgcx"
        res.then().assertThat().statusCode(200);
    }
    //Playlist --> Remove Playlist Items
    @Test
    public void delete_RemovePlaylistItems(){
        Response res = given()
                .header("accept","application/json")
                .header("Content-Type","application/json")
                .header("Authorization","Bearer "+token)
                .body("{\n" +
                        "    \"tracks\": [\n" +
                        "        {\n" +
                        "            \"uri\": \"spotify:track:1BpvuKIG7jK5EiTp2tv5z8\"\n" +
                        "        }\n" +
                        "    ]\n" +
                        "}")
                .when()
                .delete("https://api.spotify.com/v1/playlists/7FNnHCoWKCkYM5gyoVNU9h/tracks");
        res.prettyPrint();
        // "snapshot_id": "NywwOTVkNGRkNWQyZWQ2ZmM1MmM1ODcxMmRhMjc5OTk1Yzk1MjhmYjM1"
        res.then().assertThat().statusCode(200);
    }
    // Search
    @Test
    public void search1(){
        Response res = given()
                .header("accept","application/json")
                .header("Content-Type","application/json")
                .header("Authorization","Bearer "+token)
                .pathParam("q","Purpose (Deluxe)")
                .pathParam("type","album")
                .when()
                .get("https://api.spotify.com/v1/search?q={q}&type={type}");
        res.prettyPrint();
        // "snapshot_id": "NywwOTVkNGRkNWQyZWQ2ZmM1MmM1ODcxMmRhMjc5OTk1Yzk1MjhmYjM1"
//        res.then().assertThat().statusCode(200);
    }
    @Test
    public void search2() {
        Response res = given()
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .queryParam("q", "vijay")
                .queryParam("type", "album")
                .when()
                .get("https://api.spotify.com/v1/search");
        res.prettyPrint();
    }
}
