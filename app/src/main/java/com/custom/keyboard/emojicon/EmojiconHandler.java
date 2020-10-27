/*
 * Copyright 2014 Ankush Sachdeva
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.custom.keyboard.emojicon;

import android.content.Context;
import android.text.Spannable;
import android.util.SparseIntArray;
import android.util.SparseLongArray;

import com.custom.keyboard.R;

import java.util.HashMap;

public final class EmojiconHandler {
    private EmojiconHandler() {
    }

    // private static final SparseIntArray sEmojisMap = new SparseIntArray(846);
    private static final HashMap<String,Integer> sEmojisMap = new HashMap<String,Integer>();
    private static final HashMap<String,Integer> sComplexEmojisMap = new HashMap<String,Integer>();

    static {


        //people
        sEmojisMap.put("1f600", R.drawable.emoji_1f600); // grinning
        sEmojisMap.put("1f603", R.drawable.emoji_1f603); // smiley
        sEmojisMap.put("1f604", R.drawable.emoji_1f604); // smile
        sEmojisMap.put("1f601", R.drawable.emoji_1f601); // grin
        sEmojisMap.put("1f606", R.drawable.emoji_1f606); // laughing|satisfied
        sEmojisMap.put("1f605", R.drawable.emoji_1f605); // sweat_smile
        sEmojisMap.put("1f923", R.drawable.emoji_1f923); // rolling_on_the_floor_laughing
        sEmojisMap.put("1f602", R.drawable.emoji_1f602); // joy
        sEmojisMap.put("1f642", R.drawable.emoji_1f642); // slightly_smiling_face
        sEmojisMap.put("1f643", R.drawable.emoji_1f643); // upside_down_face
        sEmojisMap.put("1f609", R.drawable.emoji_1f609); // wink
        sEmojisMap.put("1f60a", R.drawable.emoji_1f60a); // blush
        sEmojisMap.put("1f607", R.drawable.emoji_1f607); // innocent
        sEmojisMap.put("1f970", R.drawable.emoji_1f970); // smiling_face_with_3_hearts
        sEmojisMap.put("1f60d", R.drawable.emoji_1f60d); // heart_eyes
        sEmojisMap.put("1f929", R.drawable.emoji_1f929); // star-struck|grinning_face_with_star_eyes
        sEmojisMap.put("1f618", R.drawable.emoji_1f618); // kissing_heart
        sEmojisMap.put("1f617", R.drawable.emoji_1f617); // kissing
        sEmojisMap.put("263a", R.drawable.emoji_263a); // relaxed
        // sEmojisMap.put("263a_fe0f", R.drawable.emoji_263a_fe0f); // relaxed
        sEmojisMap.put("1f61a", R.drawable.emoji_1f61a); // kissing_closed_eyes
        sEmojisMap.put("1f619", R.drawable.emoji_1f619); // kissing_smiling_eyes
        sEmojisMap.put("1f60b", R.drawable.emoji_1f60b); // yum
        sEmojisMap.put("1f61b", R.drawable.emoji_1f61b); // stuck_out_tongue
        sEmojisMap.put("1f61c", R.drawable.emoji_1f61c); // stuck_out_tongue_winking_eye
        sEmojisMap.put("1f92a", R.drawable.emoji_1f92a); // zany_face|grinning_face_with_one_large_and_one_small_eye
        sEmojisMap.put("1f61d", R.drawable.emoji_1f61d); // stuck_out_tongue_closed_eyes
        sEmojisMap.put("1f911", R.drawable.emoji_1f911); // money_mouth_face
        sEmojisMap.put("1f917", R.drawable.emoji_1f917); // hugging_face
        sEmojisMap.put("1f92d", R.drawable.emoji_1f92d); // face_with_hand_over_mouth|smiling_face_with_smiling_eyes_and_hand_covering_mouth
        sEmojisMap.put("1f92b", R.drawable.emoji_1f92b); // shushing_face|face_with_finger_covering_closed_lips
        sEmojisMap.put("1f914", R.drawable.emoji_1f914); // thinking_face
        sEmojisMap.put("1f910", R.drawable.emoji_1f910); // zipper_mouth_face
        sEmojisMap.put("1f928", R.drawable.emoji_1f928); // face_with_raised_eyebrow|face_with_one_eyebrow_raised
        sEmojisMap.put("1f610", R.drawable.emoji_1f610); // neutral_face
        sEmojisMap.put("1f611", R.drawable.emoji_1f611); // expressionless
        sEmojisMap.put("1f636", R.drawable.emoji_1f636); // no_mouth
        sEmojisMap.put("1f60f", R.drawable.emoji_1f60f); // smirk
        sEmojisMap.put("1f612", R.drawable.emoji_1f612); // unamused
        sEmojisMap.put("1f644", R.drawable.emoji_1f644); // face_with_rolling_eyes
        sEmojisMap.put("1f62c", R.drawable.emoji_1f62c); // grimacing
        sEmojisMap.put("1f925", R.drawable.emoji_1f925); // lying_face
        sEmojisMap.put("1f60c", R.drawable.emoji_1f60c); // relieved
        sEmojisMap.put("1f614", R.drawable.emoji_1f614); // pensive
        sEmojisMap.put("1f62a", R.drawable.emoji_1f62a); // sleepy
        sEmojisMap.put("1f924", R.drawable.emoji_1f924); // drooling_face
        sEmojisMap.put("1f634", R.drawable.emoji_1f634); // sleeping
        sEmojisMap.put("1f637", R.drawable.emoji_1f637); // mask
        sEmojisMap.put("1f912", R.drawable.emoji_1f912); // face_with_thermometer
        sEmojisMap.put("1f915", R.drawable.emoji_1f915); // face_with_head_bandage
        sEmojisMap.put("1f922", R.drawable.emoji_1f922); // nauseated_face
        sEmojisMap.put("1f92e", R.drawable.emoji_1f92e); // face_vomiting|face_with_open_mouth_vomiting
        sEmojisMap.put("1f927", R.drawable.emoji_1f927); // sneezing_face
        sEmojisMap.put("1f975", R.drawable.emoji_1f975); // hot_face
        sEmojisMap.put("1f976", R.drawable.emoji_1f976); // cold_face
        sEmojisMap.put("1f974", R.drawable.emoji_1f974); // woozy_face
        sEmojisMap.put("1f635", R.drawable.emoji_1f635); // dizzy_face
        sEmojisMap.put("1f92f", R.drawable.emoji_1f92f); // exploding_head|shocked_face_with_exploding_head
        sEmojisMap.put("1f920", R.drawable.emoji_1f920); // face_with_cowboy_hat
        sEmojisMap.put("1f973", R.drawable.emoji_1f973); // partying_face
        sEmojisMap.put("1f60e", R.drawable.emoji_1f60e); // sunglasses
        sEmojisMap.put("1f913", R.drawable.emoji_1f913); // nerd_face
        sEmojisMap.put("1f9d0", R.drawable.emoji_1f9d0); // face_with_monocle
        sEmojisMap.put("1f615", R.drawable.emoji_1f615); // confused
        sEmojisMap.put("1f61f", R.drawable.emoji_1f61f); // worried
        sEmojisMap.put("1f641", R.drawable.emoji_1f641); // slightly_frowning_face
        sEmojisMap.put("2639", R.drawable.emoji_2639); // white_frowning_face
        // sEmojisMap.put("2639_fe0f", R.drawable.emoji_2639_fe0f); // white_frowning_face
        sEmojisMap.put("1f62e", R.drawable.emoji_1f62e); // open_mouth
        sEmojisMap.put("1f62f", R.drawable.emoji_1f62f); // hushed
        sEmojisMap.put("1f632", R.drawable.emoji_1f632); // astonished
        sEmojisMap.put("1f633", R.drawable.emoji_1f633); // flushed
        sEmojisMap.put("1f97a", R.drawable.emoji_1f97a); // pleading_face
        sEmojisMap.put("1f626", R.drawable.emoji_1f626); // frowning
        sEmojisMap.put("1f627", R.drawable.emoji_1f627); // anguished
        sEmojisMap.put("1f628", R.drawable.emoji_1f628); // fearful
        sEmojisMap.put("1f630", R.drawable.emoji_1f630); // cold_sweat
        sEmojisMap.put("1f625", R.drawable.emoji_1f625); // disappointed_relieved
        sEmojisMap.put("1f622", R.drawable.emoji_1f622); // cry
        sEmojisMap.put("1f62d", R.drawable.emoji_1f62d); // sob
        sEmojisMap.put("1f631", R.drawable.emoji_1f631); // scream
        sEmojisMap.put("1f616", R.drawable.emoji_1f616); // confounded
        sEmojisMap.put("1f623", R.drawable.emoji_1f623); // persevere
        sEmojisMap.put("1f61e", R.drawable.emoji_1f61e); // disappointed
        sEmojisMap.put("1f613", R.drawable.emoji_1f613); // sweat
        sEmojisMap.put("1f629", R.drawable.emoji_1f629); // weary
        sEmojisMap.put("1f62b", R.drawable.emoji_1f62b); // tired_face
        sEmojisMap.put("1f971", R.drawable.emoji_1f971); // yawning_face
        sEmojisMap.put("1f624", R.drawable.emoji_1f624); // triumph
        sEmojisMap.put("1f621", R.drawable.emoji_1f621); // rage
        sEmojisMap.put("1f620", R.drawable.emoji_1f620); // angry
        sEmojisMap.put("1f92c", R.drawable.emoji_1f92c); // face_with_symbols_on_mouth|serious_face_with_symbols_covering_mouth
        sEmojisMap.put("1f608", R.drawable.emoji_1f608); // smiling_imp
        sEmojisMap.put("1f47f", R.drawable.emoji_1f47f); // imp
        sEmojisMap.put("1f480", R.drawable.emoji_1f480); // skull
        sEmojisMap.put("2620", R.drawable.emoji_2620); // skull_and_crossbones
        // sEmojisMap.put("2620_fe0f", R.drawable.emoji_2620_fe0f); // skull_and_crossbones
        sEmojisMap.put("1f4a9", R.drawable.emoji_1f4a9); // hankey|poop|shit
        sEmojisMap.put("1f921", R.drawable.emoji_1f921); // clown_face
        sEmojisMap.put("1f479", R.drawable.emoji_1f479); // japanese_ogre
        sEmojisMap.put("1f47a", R.drawable.emoji_1f47a); // japanese_goblin
        sEmojisMap.put("1f47b", R.drawable.emoji_1f47b); // ghost
        sEmojisMap.put("1f47d", R.drawable.emoji_1f47d); // alien
        sEmojisMap.put("1f47e", R.drawable.emoji_1f47e); // space_invader
        sEmojisMap.put("1f916", R.drawable.emoji_1f916); // robot_face
        sEmojisMap.put("1f63a", R.drawable.emoji_1f63a); // smiley_cat
        sEmojisMap.put("1f638", R.drawable.emoji_1f638); // smile_cat
        sEmojisMap.put("1f639", R.drawable.emoji_1f639); // joy_cat
        sEmojisMap.put("1f63b", R.drawable.emoji_1f63b); // heart_eyes_cat
        sEmojisMap.put("1f63c", R.drawable.emoji_1f63c); // smirk_cat
        sEmojisMap.put("1f63d", R.drawable.emoji_1f63d); // kissing_cat
        sEmojisMap.put("1f640", R.drawable.emoji_1f640); // scream_cat
        sEmojisMap.put("1f63f", R.drawable.emoji_1f63f); // crying_cat_face
        sEmojisMap.put("1f63e", R.drawable.emoji_1f63e); // pouting_cat
        sEmojisMap.put("1f648", R.drawable.emoji_1f648); // see_no_evil
        sEmojisMap.put("1f649", R.drawable.emoji_1f649); // hear_no_evil
        sEmojisMap.put("1f64a", R.drawable.emoji_1f64a); // speak_no_evil
        sEmojisMap.put("1f48b", R.drawable.emoji_1f48b); // kiss
        sEmojisMap.put("1f48c", R.drawable.emoji_1f48c); // love_letter
        sEmojisMap.put("1f498", R.drawable.emoji_1f498); // cupid
        sEmojisMap.put("1f49d", R.drawable.emoji_1f49d); // gift_heart
        sEmojisMap.put("1f496", R.drawable.emoji_1f496); // sparkling_heart
        sEmojisMap.put("1f497", R.drawable.emoji_1f497); // heartpulse
        sEmojisMap.put("1f493", R.drawable.emoji_1f493); // heartbeat
        sEmojisMap.put("1f49e", R.drawable.emoji_1f49e); // revolving_hearts
        sEmojisMap.put("1f495", R.drawable.emoji_1f495); // two_hearts
        sEmojisMap.put("1f49f", R.drawable.emoji_1f49f); // heart_decoration
        sEmojisMap.put("2763", R.drawable.emoji_2763); // heavy_heart_exclamation_mark_ornament
        // sEmojisMap.put("2763_fe0f", R.drawable.emoji_2763_fe0f); // heavy_heart_exclamation_mark_ornament
        sEmojisMap.put("1f494", R.drawable.emoji_1f494); // broken_heart
        sEmojisMap.put("2764", R.drawable.emoji_2764); // heart
        // sEmojisMap.put("2764_fe0f", R.drawable.emoji_2764_fe0f); // heart
        sEmojisMap.put("1f9e1", R.drawable.emoji_1f9e1); // orange_heart
        sEmojisMap.put("1f49b", R.drawable.emoji_1f49b); // yellow_heart
        sEmojisMap.put("1f49a", R.drawable.emoji_1f49a); // green_heart
        sEmojisMap.put("1f499", R.drawable.emoji_1f499); // blue_heart
        sEmojisMap.put("1f49c", R.drawable.emoji_1f49c); // purple_heart
        sEmojisMap.put("1f90e", R.drawable.emoji_1f90e); // brown_heart
        sEmojisMap.put("1f5a4", R.drawable.emoji_1f5a4); // black_heart
        sEmojisMap.put("1f90d", R.drawable.emoji_1f90d); // white_heart
        sEmojisMap.put("1f4af", R.drawable.emoji_1f4af); // 100
        sEmojisMap.put("1f4a2", R.drawable.emoji_1f4a2); // anger
        sEmojisMap.put("1f4a5", R.drawable.emoji_1f4a5); // boom|collision
        sEmojisMap.put("1f4ab", R.drawable.emoji_1f4ab); // dizzy
        sEmojisMap.put("1f4a6", R.drawable.emoji_1f4a6); // sweat_drops
        sEmojisMap.put("1f4a8", R.drawable.emoji_1f4a8); // dash
        sEmojisMap.put("1f573_fe0f", R.drawable.emoji_1f573_fe0f); // hole
        sEmojisMap.put("1f4a3", R.drawable.emoji_1f4a3); // bomb
        sEmojisMap.put("1f4ac", R.drawable.emoji_1f4ac); // speech_balloon
        sEmojisMap.put("1f441_200d_1f5e8_fe0f", R.drawable.emoji_1f441_fe0f_200d_1f5e8_fe0f); // eye-in-speech-bubble
        sEmojisMap.put("1f5e8_fe0f", R.drawable.emoji_1f5e8_fe0f); // left_speech_bubble
        sEmojisMap.put("1f5ef_fe0f", R.drawable.emoji_1f5ef_fe0f); // right_anger_bubble
        sEmojisMap.put("1f4ad", R.drawable.emoji_1f4ad); // thought_balloon
        sEmojisMap.put("1f4a4", R.drawable.emoji_1f4a4); // zzz
        sEmojisMap.put("1f44b", R.drawable.emoji_1f44b); // wave
        sEmojisMap.put("1f44b_1f3fb", R.drawable.emoji_1f44b_1f3fb); // }, new String[0], 13, 27, false,
        sEmojisMap.put("1f44b_1f3fc", R.drawable.emoji_1f44b_1f3fc); // }, new String[0], 13, 28, false,
        sEmojisMap.put("1f44b_1f3fd", R.drawable.emoji_1f44b_1f3fd); // }, new String[0], 13, 29, false,
        sEmojisMap.put("1f44b_1f3fe", R.drawable.emoji_1f44b_1f3fe); // }, new String[0], 13, 30, false,
        sEmojisMap.put("1f44b_1f3ff", R.drawable.emoji_1f44b_1f3ff); // }, new String[0], 13, 31, false
         sEmojisMap.put("1f91a", R.drawable.emoji_1f91a); // raised_back_of_hand
        sEmojisMap.put("1f91a_1f3fb", R.drawable.emoji_1f91a_1f3fb); // }, new String[0], 37, 44, false,
        sEmojisMap.put("1f91a_1f3fc", R.drawable.emoji_1f91a_1f3fc); // }, new String[0], 37, 45, false,
        sEmojisMap.put("1f91a_1f3fd", R.drawable.emoji_1f91a_1f3fd); // }, new String[0], 37, 46, false,
        sEmojisMap.put("1f91a_1f3fe", R.drawable.emoji_1f91a_1f3fe); // }, new String[0], 37, 47, false,
        sEmojisMap.put("1f91a_1f3ff", R.drawable.emoji_1f91a_1f3ff); // }, new String[0], 37, 48, false          sEmojisMap.put("1f590_fe0f", R.drawable.emoji_1f590_fe0f); // raised_hand_with_fingers_splayed
        sEmojisMap.put("1f590_1f3fb", R.drawable.emoji_1f590_1f3fb); // }, new String[0], 29, 49, false,
        sEmojisMap.put("1f590_1f3fc", R.drawable.emoji_1f590_1f3fc); // }, new String[0], 29, 50, false,
        sEmojisMap.put("1f590_1f3fd", R.drawable.emoji_1f590_1f3fd); // }, new String[0], 29, 51, false,
        sEmojisMap.put("1f590_1f3fe", R.drawable.emoji_1f590_1f3fe); // }, new String[0], 29, 52, false,
        sEmojisMap.put("1f590_1f3ff", R.drawable.emoji_1f590_1f3ff); // }, new String[0], 29, 53, false
 sEmojisMap.put("270b", R.drawable.emoji_270b); // hand|raised_hand
        sEmojisMap.put("270b_1f3fb", R.drawable.emoji_270b_1f3fb); // }, new String[0], 54, 50, false,
        sEmojisMap.put("270b_1f3fc", R.drawable.emoji_270b_1f3fc); // }, new String[0], 54, 51, false,
        sEmojisMap.put("270b_1f3fd", R.drawable.emoji_270b_1f3fd); // }, new String[0], 54, 52, false,
        sEmojisMap.put("270b_1f3fe", R.drawable.emoji_270b_1f3fe); // }, new String[0], 54, 53, false,
        sEmojisMap.put("270b_1f3ff", R.drawable.emoji_270b_1f3ff); // }, new String[0], 54, 54, false
        sEmojisMap.put("1f596", R.drawable.emoji_1f596); // spock-hand
        sEmojisMap.put("1f596_1f3fb", R.drawable.emoji_1f596_1f3fb); // }, new String[0], 30, 4, false,
        sEmojisMap.put("1f596_1f3fc", R.drawable.emoji_1f596_1f3fc); // }, new String[0], 30, 5, false,
        sEmojisMap.put("1f596_1f3fd", R.drawable.emoji_1f596_1f3fd); // }, new String[0], 30, 6, false,
        sEmojisMap.put("1f596_1f3fe", R.drawable.emoji_1f596_1f3fe); // }, new String[0], 30, 7, false,
        sEmojisMap.put("1f596_1f3ff", R.drawable.emoji_1f596_1f3ff); // }, new String[0], 30, 8, false
        sEmojisMap.put("1f44c", R.drawable.emoji_1f44c); // ok_hand
        sEmojisMap.put("1f44c_1f3fb", R.drawable.emoji_1f44c_1f3fb); // }, new String[0], 13, 33, false,
        sEmojisMap.put("1f44c_1f3fc", R.drawable.emoji_1f44c_1f3fc); // }, new String[0], 13, 34, false,
        sEmojisMap.put("1f44c_1f3fd", R.drawable.emoji_1f44c_1f3fd); // }, new String[0], 13, 35, false,
        sEmojisMap.put("1f44c_1f3fe", R.drawable.emoji_1f44c_1f3fe); // }, new String[0], 13, 36, false,
        sEmojisMap.put("1f44c_1f3ff", R.drawable.emoji_1f44c_1f3ff); // }, new String[0], 13, 37, false
        sEmojisMap.put("1f90f", R.drawable.emoji_1f90f); // pinching_hand
        sEmojisMap.put("1f90f_1f3fb", R.drawable.emoji_1f90f_1f3fb); // }, new String[0], 37, 18, false,
        sEmojisMap.put("1f90f_1f3fc", R.drawable.emoji_1f90f_1f3fc); // }, new String[0], 37, 19, false,
        sEmojisMap.put("1f90f_1f3fd", R.drawable.emoji_1f90f_1f3fd); // }, new String[0], 37, 20, false,
        sEmojisMap.put("1f90f_1f3fe", R.drawable.emoji_1f90f_1f3fe); // }, new String[0], 37, 21, false,
        sEmojisMap.put("1f90f_1f3ff", R.drawable.emoji_1f90f_1f3ff); // }, new String[0], 37, 22, false
        sEmojisMap.put("270c", R.drawable.emoji_270c); // v
        // sEmojisMap.put("270c_fe0f", R.drawable.emoji_270c_fe0f); // v
        sEmojisMap.put("270c_1f3fb", R.drawable.emoji_270c_1f3fb); // }, new String[0], 54, 56, false,
        sEmojisMap.put("270c_1f3fc", R.drawable.emoji_270c_1f3fc); // }, new String[0], 55, 0, false,
        sEmojisMap.put("270c_1f3fd", R.drawable.emoji_270c_1f3fd); // }, new String[0], 55, 1, false,
        sEmojisMap.put("270c_1f3fe", R.drawable.emoji_270c_1f3fe); // }, new String[0], 55, 2, false,
        sEmojisMap.put("270c_1f3ff", R.drawable.emoji_270c_1f3ff); // }, new String[0], 55, 3, false
        sEmojisMap.put("1f91e", R.drawable.emoji_1f91e); // crossed_fingers|hand_with_index_and_middle_fingers_crossed
        sEmojisMap.put("1f91e_1f3fb", R.drawable.emoji_1f91e_1f3fb); // }, new String[0], 38, 6, false,
        sEmojisMap.put("1f91e_1f3fc", R.drawable.emoji_1f91e_1f3fc); // }, new String[0], 38, 7, false,
        sEmojisMap.put("1f91e_1f3fd", R.drawable.emoji_1f91e_1f3fd); // }, new String[0], 38, 8, false,
        sEmojisMap.put("1f91e_1f3fe", R.drawable.emoji_1f91e_1f3fe); // }, new String[0], 38, 9, false,
        sEmojisMap.put("1f91e_1f3ff", R.drawable.emoji_1f91e_1f3ff); // }, new String[0], 38, 10, false
        sEmojisMap.put("1f91f", R.drawable.emoji_1f91f); // i_love_you_hand_sign
        sEmojisMap.put("1f91f_1f3fb", R.drawable.emoji_1f91f_1f3fb); // }, new String[0], 38, 12, false,
        sEmojisMap.put("1f91f_1f3fc", R.drawable.emoji_1f91f_1f3fc); // }, new String[0], 38, 13, false,
        sEmojisMap.put("1f91f_1f3fd", R.drawable.emoji_1f91f_1f3fd); // }, new String[0], 38, 14, false,
        sEmojisMap.put("1f91f_1f3fe", R.drawable.emoji_1f91f_1f3fe); // }, new String[0], 38, 15, false,
        sEmojisMap.put("1f91f_1f3ff", R.drawable.emoji_1f91f_1f3ff); // }, new String[0], 38, 16, false
        sEmojisMap.put("1f918", R.drawable.emoji_1f918); // the_horns|sign_of_the_horns
        sEmojisMap.put("1f918_1f3fb", R.drawable.emoji_1f918_1f3fb); // }, new String[0], 37, 32, false,
        sEmojisMap.put("1f918_1f3fc", R.drawable.emoji_1f918_1f3fc); // }, new String[0], 37, 33, false,
        sEmojisMap.put("1f918_1f3fd", R.drawable.emoji_1f918_1f3fd); // }, new String[0], 37, 34, false,
        sEmojisMap.put("1f918_1f3fe", R.drawable.emoji_1f918_1f3fe); // }, new String[0], 37, 35, false,
        sEmojisMap.put("1f918_1f3ff", R.drawable.emoji_1f918_1f3ff); // }, new String[0], 37, 36, false
        sEmojisMap.put("1f919", R.drawable.emoji_1f919); // call_me_hand
        sEmojisMap.put("1f919_1f3fb", R.drawable.emoji_1f919_1f3fb); // }, new String[0], 37, 38, false,
        sEmojisMap.put("1f919_1f3fc", R.drawable.emoji_1f919_1f3fc); // }, new String[0], 37, 39, false,
        sEmojisMap.put("1f919_1f3fd", R.drawable.emoji_1f919_1f3fd); // }, new String[0], 37, 40, false,
        sEmojisMap.put("1f919_1f3fe", R.drawable.emoji_1f919_1f3fe); // }, new String[0], 37, 41, false,
        sEmojisMap.put("1f919_1f3ff", R.drawable.emoji_1f919_1f3ff); // }, new String[0], 37, 42, false
        sEmojisMap.put("1f448", R.drawable.emoji_1f448); // point_left
        sEmojisMap.put("1f448_1f3fb", R.drawable.emoji_1f448_1f3fb); // }, new String[0], 13, 9, false,
        sEmojisMap.put("1f448_1f3fc", R.drawable.emoji_1f448_1f3fc); // }, new String[0], 13, 10, false,
        sEmojisMap.put("1f448_1f3fd", R.drawable.emoji_1f448_1f3fd); // }, new String[0], 13, 11, false,
        sEmojisMap.put("1f448_1f3fe", R.drawable.emoji_1f448_1f3fe); // }, new String[0], 13, 12, false,
        sEmojisMap.put("1f448_1f3ff", R.drawable.emoji_1f448_1f3ff); // }, new String[0], 13, 13, false
        sEmojisMap.put("1f449", R.drawable.emoji_1f449); // point_right
        sEmojisMap.put("1f449_1f3fb", R.drawable.emoji_1f449_1f3fb); // }, new String[0], 13, 15, false,
        sEmojisMap.put("1f449_1f3fc", R.drawable.emoji_1f449_1f3fc); // }, new String[0], 13, 16, false,
        sEmojisMap.put("1f449_1f3fd", R.drawable.emoji_1f449_1f3fd); // }, new String[0], 13, 17, false,
        sEmojisMap.put("1f449_1f3fe", R.drawable.emoji_1f449_1f3fe); // }, new String[0], 13, 18, false,
        sEmojisMap.put("1f449_1f3ff", R.drawable.emoji_1f449_1f3ff); // }, new String[0], 13, 19, false
        sEmojisMap.put("1f446", R.drawable.emoji_1f446); // point_up_2
        sEmojisMap.put("1f446_1f3fb", R.drawable.emoji_1f446_1f3fb); // }, new String[0], 12, 54, false,
        sEmojisMap.put("1f446_1f3fc", R.drawable.emoji_1f446_1f3fc); // }, new String[0], 12, 55, false,
        sEmojisMap.put("1f446_1f3fd", R.drawable.emoji_1f446_1f3fd); // }, new String[0], 12, 56, false,
        sEmojisMap.put("1f446_1f3fe", R.drawable.emoji_1f446_1f3fe); // }, new String[0], 13, 0, false,
        sEmojisMap.put("1f446_1f3ff", R.drawable.emoji_1f446_1f3ff); // }, new String[0], 13, 1, false
        sEmojisMap.put("1f595", R.drawable.emoji_1f595); // middle_finger|reversed_hand_with_middle_finger_extended
        sEmojisMap.put("1f595_1f3fb", R.drawable.emoji_1f595_1f3fb); // }, new String[0], 29, 55, false,
        sEmojisMap.put("1f595_1f3fc", R.drawable.emoji_1f595_1f3fc); // }, new String[0], 29, 56, false,
        sEmojisMap.put("1f595_1f3fd", R.drawable.emoji_1f595_1f3fd); // }, new String[0], 30, 0, false,
        sEmojisMap.put("1f595_1f3fe", R.drawable.emoji_1f595_1f3fe); // }, new String[0], 30, 1, false,
        sEmojisMap.put("1f595_1f3ff", R.drawable.emoji_1f595_1f3ff); // }, new String[0], 30, 2, false
        sEmojisMap.put("1f447", R.drawable.emoji_1f447); // point_down
        sEmojisMap.put("1f447_1f3fb", R.drawable.emoji_1f447_1f3fb); // }, new String[0], 13, 3, false,
        sEmojisMap.put("1f447_1f3fc", R.drawable.emoji_1f447_1f3fc); // }, new String[0], 13, 4, false,
        sEmojisMap.put("1f447_1f3fd", R.drawable.emoji_1f447_1f3fd); // }, new String[0], 13, 5, false,
        sEmojisMap.put("1f447_1f3fe", R.drawable.emoji_1f447_1f3fe); // }, new String[0], 13, 6, false,
        sEmojisMap.put("1f447_1f3ff", R.drawable.emoji_1f447_1f3ff); // }, new String[0], 13, 7, false
        sEmojisMap.put("261d", R.drawable.emoji_261d); // point_up
        // sEmojisMap.put("261d_fe0f", R.drawable.emoji_261d_fe0f); // point_up
        sEmojisMap.put("261d_1f3fb", R.drawable.emoji_261d_1f3fb); // }, new String[0], 53, 3, false,
        sEmojisMap.put("261d_1f3fc", R.drawable.emoji_261d_1f3fc); // }, new String[0], 53, 4, false,
        sEmojisMap.put("261d_1f3fd", R.drawable.emoji_261d_1f3fd); // }, new String[0], 53, 5, false,
        sEmojisMap.put("261d_1f3fe", R.drawable.emoji_261d_1f3fe); // }, new String[0], 53, 6, false,
        sEmojisMap.put("261d_1f3ff", R.drawable.emoji_261d_1f3ff); // }, new String[0], 53, 7, false
        sEmojisMap.put("1f44d", R.drawable.emoji_1f44d); // +1|thumbsup
        sEmojisMap.put("1f44d_1f3fb", R.drawable.emoji_1f44d_1f3fb); // }, new String[0], 13, 39, false,
        sEmojisMap.put("1f44d_1f3fc", R.drawable.emoji_1f44d_1f3fc); // }, new String[0], 13, 40, false,
        sEmojisMap.put("1f44d_1f3fd", R.drawable.emoji_1f44d_1f3fd); // }, new String[0], 13, 41, false,
        sEmojisMap.put("1f44d_1f3fe", R.drawable.emoji_1f44d_1f3fe); // }, new String[0], 13, 42, false,
        sEmojisMap.put("1f44d_1f3ff", R.drawable.emoji_1f44d_1f3ff); // }, new String[0], 13, 43, false
        sEmojisMap.put("1f44e", R.drawable.emoji_1f44e); // -1|thumbsdown
        sEmojisMap.put("1f44e_1f3fb", R.drawable.emoji_1f44e_1f3fb); // }, new String[0], 13, 45, false,
        sEmojisMap.put("1f44e_1f3fc", R.drawable.emoji_1f44e_1f3fc); // }, new String[0], 13, 46, false,
        sEmojisMap.put("1f44e_1f3fd", R.drawable.emoji_1f44e_1f3fd); // }, new String[0], 13, 47, false,
        sEmojisMap.put("1f44e_1f3fe", R.drawable.emoji_1f44e_1f3fe); // }, new String[0], 13, 48, false,
        sEmojisMap.put("1f44e_1f3ff", R.drawable.emoji_1f44e_1f3ff); // }, new String[0], 13, 49, false
         sEmojisMap.put("270a", R.drawable.emoji_270a); // fist
        sEmojisMap.put("270a_1f3fb", R.drawable.emoji_270a_1f3fb); // }, new String[0], 54, 44, false,
        sEmojisMap.put("270a_1f3fc", R.drawable.emoji_270a_1f3fc); // }, new String[0], 54, 45, false,
        sEmojisMap.put("270a_1f3fd", R.drawable.emoji_270a_1f3fd); // }, new String[0], 54, 46, false,
        sEmojisMap.put("270a_1f3fe", R.drawable.emoji_270a_1f3fe); // }, new String[0], 54, 47, false,
        sEmojisMap.put("270a_1f3ff", R.drawable.emoji_270a_1f3ff); // }, new String[0], 54, 48, false
        sEmojisMap.put("1f44a", R.drawable.emoji_1f44a); // facepunch|punch
        sEmojisMap.put("1f44a_1f3fb", R.drawable.emoji_1f44a_1f3fb); // }, new String[0], 13, 21, false,
        sEmojisMap.put("1f44a_1f3fc", R.drawable.emoji_1f44a_1f3fc); // }, new String[0], 13, 22, false,
        sEmojisMap.put("1f44a_1f3fd", R.drawable.emoji_1f44a_1f3fd); // }, new String[0], 13, 23, false,
        sEmojisMap.put("1f44a_1f3fe", R.drawable.emoji_1f44a_1f3fe); // }, new String[0], 13, 24, false,
        sEmojisMap.put("1f44a_1f3ff", R.drawable.emoji_1f44a_1f3ff); // }, new String[0], 13, 25, false
        sEmojisMap.put("1f91b", R.drawable.emoji_1f91b); // left-facing_fist
        sEmojisMap.put("1f91b_1f3fb", R.drawable.emoji_1f91b_1f3fb); // }, new String[0], 37, 50, false,
        sEmojisMap.put("1f91b_1f3fc", R.drawable.emoji_1f91b_1f3fc); // }, new String[0], 37, 51, false,
        sEmojisMap.put("1f91b_1f3fd", R.drawable.emoji_1f91b_1f3fd); // }, new String[0], 37, 52, false,
        sEmojisMap.put("1f91b_1f3fe", R.drawable.emoji_1f91b_1f3fe); // }, new String[0], 37, 53, false,
        sEmojisMap.put("1f91b_1f3ff", R.drawable.emoji_1f91b_1f3ff); // }, new String[0], 37, 54, false
        sEmojisMap.put("1f91c", R.drawable.emoji_1f91c); // right-facing_fist
        sEmojisMap.put("1f91c_1f3fb", R.drawable.emoji_1f91c_1f3fb); // }, new String[0], 37, 56, false,
        sEmojisMap.put("1f91c_1f3fc", R.drawable.emoji_1f91c_1f3fc); // }, new String[0], 38, 0, false,
        sEmojisMap.put("1f91c_1f3fd", R.drawable.emoji_1f91c_1f3fd); // }, new String[0], 38, 1, false,
        sEmojisMap.put("1f91c_1f3fe", R.drawable.emoji_1f91c_1f3fe); // }, new String[0], 38, 2, false,
        sEmojisMap.put("1f91c_1f3ff", R.drawable.emoji_1f91c_1f3ff); // }, new String[0], 38, 3, false
        sEmojisMap.put("1f44f", R.drawable.emoji_1f44f); // clap
        sEmojisMap.put("1f44f_1f3fb", R.drawable.emoji_1f44f_1f3fb); // }, new String[0], 13, 51, false,
        sEmojisMap.put("1f44f_1f3fc", R.drawable.emoji_1f44f_1f3fc); // }, new String[0], 13, 52, false,
        sEmojisMap.put("1f44f_1f3fd", R.drawable.emoji_1f44f_1f3fd); // }, new String[0], 13, 53, false,
        sEmojisMap.put("1f44f_1f3fe", R.drawable.emoji_1f44f_1f3fe); // }, new String[0], 13, 54, false,
        sEmojisMap.put("1f44f_1f3ff", R.drawable.emoji_1f44f_1f3ff); // }, new String[0], 13, 55, false   sEmojisMap.put("1f64c", R.drawable.emoji_1f64c); // raised_hands
        sEmojisMap.put("1f64c_1f3fb", R.drawable.emoji_1f64c_1f3fb); // }, new String[0], 33, 9, false,
        sEmojisMap.put("1f64c_1f3fc", R.drawable.emoji_1f64c_1f3fc); // }, new String[0], 33, 10, false,
        sEmojisMap.put("1f64c_1f3fd", R.drawable.emoji_1f64c_1f3fd); // }, new String[0], 33, 11, false,
        sEmojisMap.put("1f64c_1f3fe", R.drawable.emoji_1f64c_1f3fe); // }, new String[0], 33, 12, false,
        sEmojisMap.put("1f64c_1f3ff", R.drawable.emoji_1f64c_1f3ff); // }, new String[0], 33, 13, false
        sEmojisMap.put("1f450", R.drawable.emoji_1f450); // open_hands
        sEmojisMap.put("1f450_1f3fb", R.drawable.emoji_1f450_1f3fb); // }, new String[0], 14, 0, false,
        sEmojisMap.put("1f450_1f3fc", R.drawable.emoji_1f450_1f3fc); // }, new String[0], 14, 1, false,
        sEmojisMap.put("1f450_1f3fd", R.drawable.emoji_1f450_1f3fd); // }, new String[0], 14, 2, false,
        sEmojisMap.put("1f450_1f3fe", R.drawable.emoji_1f450_1f3fe); // }, new String[0], 14, 3, false,
        sEmojisMap.put("1f450_1f3ff", R.drawable.emoji_1f450_1f3ff); // }, new String[0], 14, 4, false
        sEmojisMap.put("1f932", R.drawable.emoji_1f932); // palms_up_together
        sEmojisMap.put("1f932_1f3fb", R.drawable.emoji_1f932_1f3fb); // }, new String[0], 39, 6, false,
        sEmojisMap.put("1f932_1f3fc", R.drawable.emoji_1f932_1f3fc); // }, new String[0], 39, 7, false,
        sEmojisMap.put("1f932_1f3fd", R.drawable.emoji_1f932_1f3fd); // }, new String[0], 39, 8, false,
        sEmojisMap.put("1f932_1f3fe", R.drawable.emoji_1f932_1f3fe); // }, new String[0], 39, 9, false,
        sEmojisMap.put("1f932_1f3ff", R.drawable.emoji_1f932_1f3ff); // }, new String[0], 39, 10, false
        sEmojisMap.put("1f91d", R.drawable.emoji_1f91d); // handshake
        sEmojisMap.put("1f64f", R.drawable.emoji_1f64f); // pray
        sEmojisMap.put("1f64f_1f3fb", R.drawable.emoji_1f64f_1f3fb); // }, new String[0], 33, 51, false,
        sEmojisMap.put("1f64f_1f3fc", R.drawable.emoji_1f64f_1f3fc); // }, new String[0], 33, 52, false,
        sEmojisMap.put("1f64f_1f3fd", R.drawable.emoji_1f64f_1f3fd); // }, new String[0], 33, 53, false,
        sEmojisMap.put("1f64f_1f3fe", R.drawable.emoji_1f64f_1f3fe); // }, new String[0], 33, 54, false,
        sEmojisMap.put("1f64f_1f3ff", R.drawable.emoji_1f64f_1f3ff); // }, new String[0], 33, 55, false
        sEmojisMap.put("270d", R.drawable.emoji_270d); // writing_hand
        // sEmojisMap.put("270d_fe0f", R.drawable.emoji_270d_fe0f); // writing_hand
        sEmojisMap.put("270d_1f3fb", R.drawable.emoji_270d_1f3fb); // }, new String[0], 55, 5, false,
        sEmojisMap.put("270d_1f3fc", R.drawable.emoji_270d_1f3fc); // }, new String[0], 55, 6, false,
        sEmojisMap.put("270d_1f3fd", R.drawable.emoji_270d_1f3fd); // }, new String[0], 55, 7, false,
        sEmojisMap.put("270d_1f3fe", R.drawable.emoji_270d_1f3fe); // }, new String[0], 55, 8, false,
        sEmojisMap.put("270d_1f3ff", R.drawable.emoji_270d_1f3ff); // }, new String[0], 55, 9, false
        sEmojisMap.put("1f485", R.drawable.emoji_1f485); // nail_care
        sEmojisMap.put("1f485_1f3fb", R.drawable.emoji_1f485_1f3fb); // }, new String[0], 24, 34, false,
        sEmojisMap.put("1f485_1f3fc", R.drawable.emoji_1f485_1f3fc); // }, new String[0], 24, 35, false,
        sEmojisMap.put("1f485_1f3fd", R.drawable.emoji_1f485_1f3fd); // }, new String[0], 24, 36, false,
        sEmojisMap.put("1f485_1f3fe", R.drawable.emoji_1f485_1f3fe); // }, new String[0], 24, 37, false,
        sEmojisMap.put("1f485_1f3ff", R.drawable.emoji_1f485_1f3ff); // }, new String[0], 24, 38, false
        sEmojisMap.put("1f933", R.drawable.emoji_1f933); // selfie
        sEmojisMap.put("1f933_1f3fb", R.drawable.emoji_1f933_1f3fb); // }, new String[0], 39, 12, false,
        sEmojisMap.put("1f933_1f3fc", R.drawable.emoji_1f933_1f3fc); // }, new String[0], 39, 13, false,
        sEmojisMap.put("1f933_1f3fd", R.drawable.emoji_1f933_1f3fd); // }, new String[0], 39, 14, false,
        sEmojisMap.put("1f933_1f3fe", R.drawable.emoji_1f933_1f3fe); // }, new String[0], 39, 15, false,
        sEmojisMap.put("1f933_1f3ff", R.drawable.emoji_1f933_1f3ff); // }, new String[0], 39, 16, false
         sEmojisMap.put("1f4aa", R.drawable.emoji_1f4aa); // muscle
        sEmojisMap.put("1f4aa_1f3fb", R.drawable.emoji_1f4aa_1f3fb); // }, new String[0], 25, 53, false,
        sEmojisMap.put("1f4aa_1f3fc", R.drawable.emoji_1f4aa_1f3fc); // }, new String[0], 25, 54, false,
        sEmojisMap.put("1f4aa_1f3fd", R.drawable.emoji_1f4aa_1f3fd); // }, new String[0], 25, 55, false,
        sEmojisMap.put("1f4aa_1f3fe", R.drawable.emoji_1f4aa_1f3fe); // }, new String[0], 25, 56, false,
        sEmojisMap.put("1f4aa_1f3ff", R.drawable.emoji_1f4aa_1f3ff); // }, new String[0], 26, 0, false
        sEmojisMap.put("1f9be", R.drawable.emoji_1f9be); // mechanical_arm
        sEmojisMap.put("1f9bf", R.drawable.emoji_1f9bf); // mechanical_leg
        sEmojisMap.put("1f9b5", R.drawable.emoji_1f9b5); // leg
        sEmojisMap.put("1f9b5_1f3fb", R.drawable.emoji_1f9b5_1f3fb); // }, new String[0], 43, 6, false,
        sEmojisMap.put("1f9b5_1f3fc", R.drawable.emoji_1f9b5_1f3fc); // }, new String[0], 43, 7, false,
        sEmojisMap.put("1f9b5_1f3fd", R.drawable.emoji_1f9b5_1f3fd); // }, new String[0], 43, 8, false,
        sEmojisMap.put("1f9b5_1f3fe", R.drawable.emoji_1f9b5_1f3fe); // }, new String[0], 43, 9, false,
        sEmojisMap.put("1f9b5_1f3ff", R.drawable.emoji_1f9b5_1f3ff); // }, new String[0], 43, 10, false
        sEmojisMap.put("1f9b6", R.drawable.emoji_1f9b6); // foot
        sEmojisMap.put("1f9b6_1f3fb", R.drawable.emoji_1f9b6_1f3fb); // }, new String[0], 43, 12, false,
        sEmojisMap.put("1f9b6_1f3fc", R.drawable.emoji_1f9b6_1f3fc); // }, new String[0], 43, 13, false,
        sEmojisMap.put("1f9b6_1f3fd", R.drawable.emoji_1f9b6_1f3fd); // }, new String[0], 43, 14, false,
        sEmojisMap.put("1f9b6_1f3fe", R.drawable.emoji_1f9b6_1f3fe); // }, new String[0], 43, 15, false,
        sEmojisMap.put("1f9b6_1f3ff", R.drawable.emoji_1f9b6_1f3ff); // }, new String[0], 43, 16, false
        sEmojisMap.put("1f442", R.drawable.emoji_1f442); // ear
        sEmojisMap.put("1f442_1f3fb", R.drawable.emoji_1f442_1f3fb); // }, new String[0], 12, 40, false,
        sEmojisMap.put("1f442_1f3fc", R.drawable.emoji_1f442_1f3fc); // }, new String[0], 12, 41, false,
        sEmojisMap.put("1f442_1f3fd", R.drawable.emoji_1f442_1f3fd); // }, new String[0], 12, 42, false,
        sEmojisMap.put("1f442_1f3fe", R.drawable.emoji_1f442_1f3fe); // }, new String[0], 12, 43, false,
        sEmojisMap.put("1f442_1f3ff", R.drawable.emoji_1f442_1f3ff); // }, new String[0], 12, 44, false
        sEmojisMap.put("1f9bb", R.drawable.emoji_1f9bb); // ear_with_hearing_aid
        sEmojisMap.put("1f9bb_1f3fb", R.drawable.emoji_1f9bb_1f3fb); // }, new String[0], 43, 56, false,
        sEmojisMap.put("1f9bb_1f3fc", R.drawable.emoji_1f9bb_1f3fc); // }, new String[0], 44, 0, false,
        sEmojisMap.put("1f9bb_1f3fd", R.drawable.emoji_1f9bb_1f3fd); // }, new String[0], 44, 1, false,
        sEmojisMap.put("1f9bb_1f3fe", R.drawable.emoji_1f9bb_1f3fe); // }, new String[0], 44, 2, false,
        sEmojisMap.put("1f9bb_1f3ff", R.drawable.emoji_1f9bb_1f3ff); // }, new String[0], 44, 3, false
        sEmojisMap.put("1f443", R.drawable.emoji_1f443); // nose
        sEmojisMap.put("1f443_1f3fb", R.drawable.emoji_1f443_1f3fb); // }, new String[0], 12, 46, false,
        sEmojisMap.put("1f443_1f3fc", R.drawable.emoji_1f443_1f3fc); // }, new String[0], 12, 47, false,
        sEmojisMap.put("1f443_1f3fd", R.drawable.emoji_1f443_1f3fd); // }, new String[0], 12, 48, false,
        sEmojisMap.put("1f443_1f3fe", R.drawable.emoji_1f443_1f3fe); // }, new String[0], 12, 49, false,
        sEmojisMap.put("1f443_1f3ff", R.drawable.emoji_1f443_1f3ff); // }, new String[0], 12, 50, false
        sEmojisMap.put("1f9e0", R.drawable.emoji_1f9e0); // brain
        sEmojisMap.put("1f9b7", R.drawable.emoji_1f9b7); // tooth
        sEmojisMap.put("1f9b4", R.drawable.emoji_1f9b4); // bone
        sEmojisMap.put("1f440", R.drawable.emoji_1f440); // eyes
        sEmojisMap.put("1f441_fe0f", R.drawable.emoji_1f441_fe0f); // eye
        sEmojisMap.put("1f445", R.drawable.emoji_1f445); // tongue
        sEmojisMap.put("1f444", R.drawable.emoji_1f444); // lips
        sEmojisMap.put("1f476", R.drawable.emoji_1f476); // baby
        sEmojisMap.put("1f476_1f3fb", R.drawable.emoji_1f476_1f3fb); // }, new String[0], 23, 5, false,
        sEmojisMap.put("1f476_1f3fc", R.drawable.emoji_1f476_1f3fc); // }, new String[0], 23, 6, false,
        sEmojisMap.put("1f476_1f3fd", R.drawable.emoji_1f476_1f3fd); // }, new String[0], 23, 7, false,
        sEmojisMap.put("1f476_1f3fe", R.drawable.emoji_1f476_1f3fe); // }, new String[0], 23, 8, false,
        sEmojisMap.put("1f476_1f3ff", R.drawable.emoji_1f476_1f3ff); // }, new String[0], 23, 9, false
        sEmojisMap.put("1f9d2", R.drawable.emoji_1f9d2); // child
        sEmojisMap.put("1f9d2_1f3fb", R.drawable.emoji_1f9d2_1f3fb); // }, new String[0], 48, 17, false,
        sEmojisMap.put("1f9d2_1f3fc", R.drawable.emoji_1f9d2_1f3fc); // }, new String[0], 48, 18, false,
        sEmojisMap.put("1f9d2_1f3fd", R.drawable.emoji_1f9d2_1f3fd); // }, new String[0], 48, 19, false,
        sEmojisMap.put("1f9d2_1f3fe", R.drawable.emoji_1f9d2_1f3fe); // }, new String[0], 48, 20, false,
        sEmojisMap.put("1f9d2_1f3ff", R.drawable.emoji_1f9d2_1f3ff); // }, new String[0], 48, 21, false
        sEmojisMap.put("1f466", R.drawable.emoji_1f466); // boy
        sEmojisMap.put("1f466_1f3fb", R.drawable.emoji_1f466_1f3fb); // }, new String[0], 14, 27, false,
        sEmojisMap.put("1f466_1f3fc", R.drawable.emoji_1f466_1f3fc); // }, new String[0], 14, 28, false,
        sEmojisMap.put("1f466_1f3fd", R.drawable.emoji_1f466_1f3fd); // }, new String[0], 14, 29, false,
        sEmojisMap.put("1f466_1f3fe", R.drawable.emoji_1f466_1f3fe); // }, new String[0], 14, 30, false,
        sEmojisMap.put("1f466_1f3ff", R.drawable.emoji_1f466_1f3ff); // }, new String[0], 14, 31, false
        sEmojisMap.put("1f467", R.drawable.emoji_1f467); // girl
        sEmojisMap.put("1f467_1f3fb", R.drawable.emoji_1f467_1f3fb); // }, new String[0], 14, 33, false,
        sEmojisMap.put("1f467_1f3fc", R.drawable.emoji_1f467_1f3fc); // }, new String[0], 14, 34, false,
        sEmojisMap.put("1f467_1f3fd", R.drawable.emoji_1f467_1f3fd); // }, new String[0], 14, 35, false,
        sEmojisMap.put("1f467_1f3fe", R.drawable.emoji_1f467_1f3fe); // }, new String[0], 14, 36, false,
        sEmojisMap.put("1f467_1f3ff", R.drawable.emoji_1f467_1f3ff); // }, new String[0], 14, 37, false
        sEmojisMap.put("1f9d1", R.drawable.emoji_1f9d1); // adult
        sEmojisMap.put("1f9d1_1f3fb", R.drawable.emoji_1f9d1_1f3fb); // }, new String[0], 48, 11, false,
        sEmojisMap.put("1f9d1_1f3fc", R.drawable.emoji_1f9d1_1f3fc); // }, new String[0], 48, 12, false,
        sEmojisMap.put("1f9d1_1f3fd", R.drawable.emoji_1f9d1_1f3fd); // }, new String[0], 48, 13, false,
        sEmojisMap.put("1f9d1_1f3fe", R.drawable.emoji_1f9d1_1f3fe); // }, new String[0], 48, 14, false,
        sEmojisMap.put("1f9d1_1f3ff", R.drawable.emoji_1f9d1_1f3ff); // }, new String[0], 48, 15, false
        sEmojisMap.put("1f471", R.drawable.emoji_1f471); // person_with_blond_hair
        sEmojisMap.put("1f471_1f3fb", R.drawable.emoji_1f471_1f3fb); // }, new String[0], 22, 20, false,
        sEmojisMap.put("1f471_1f3fc", R.drawable.emoji_1f471_1f3fc); // }, new String[0], 22, 21, false,
        sEmojisMap.put("1f471_1f3fd", R.drawable.emoji_1f471_1f3fd); // }, new String[0], 22, 22, false,
        sEmojisMap.put("1f471_1f3fe", R.drawable.emoji_1f471_1f3fe); // }, new String[0], 22, 23, false,
        sEmojisMap.put("1f471_1f3ff", R.drawable.emoji_1f471_1f3ff); // }, new String[0], 22, 24, false
        sEmojisMap.put("1f468", R.drawable.emoji_1f468); // man
        sEmojisMap.put("1f468_1f3fb", R.drawable.emoji_1f468_1f3fb); // }, new String[0], 17, 23, false,
        sEmojisMap.put("1f468_1f3fc", R.drawable.emoji_1f468_1f3fc); // }, new String[0], 17, 24, false,
        sEmojisMap.put("1f468_1f3fd", R.drawable.emoji_1f468_1f3fd); // }, new String[0], 17, 25, false,
        sEmojisMap.put("1f468_1f3fe", R.drawable.emoji_1f468_1f3fe); // }, new String[0], 17, 26, false,
        sEmojisMap.put("1f468_1f3ff", R.drawable.emoji_1f468_1f3ff); // }, new String[0], 17, 27, false
        sEmojisMap.put("1f9d4", R.drawable.emoji_1f9d4); // bearded_person
        sEmojisMap.put("1f9d4_1f3fb", R.drawable.emoji_1f9d4_1f3fb); // }, new String[0], 48, 29, false,
        sEmojisMap.put("1f9d4_1f3fc", R.drawable.emoji_1f9d4_1f3fc); // }, new String[0], 48, 30, false,
        sEmojisMap.put("1f9d4_1f3fd", R.drawable.emoji_1f9d4_1f3fd); // }, new String[0], 48, 31, false,
        sEmojisMap.put("1f9d4_1f3fe", R.drawable.emoji_1f9d4_1f3fe); // }, new String[0], 48, 32, false,
        sEmojisMap.put("1f9d4_1f3ff", R.drawable.emoji_1f9d4_1f3ff); // }, new String[0], 48, 33, false
        sEmojisMap.put("1f468_200d_1f9b0", R.drawable.emoji_1f468_200d_1f9b0); // red_haired_man
        sEmojisMap.put("1f468_1f3fb_200d_1f9b0", R.drawable.emoji_1f468_1f3fb_200d_1f9b0); // }, new String[0], 16, 24, false,
        sEmojisMap.put("1f468_1f3fc_200d_1f9b0", R.drawable.emoji_1f468_1f3fc_200d_1f9b0); // }, new String[0], 16, 25, false,
        sEmojisMap.put("1f468_1f3fd_200d_1f9b0", R.drawable.emoji_1f468_1f3fd_200d_1f9b0); // }, new String[0], 16, 26, false,
        sEmojisMap.put("1f468_1f3fe_200d_1f9b0", R.drawable.emoji_1f468_1f3fe_200d_1f9b0); // }, new String[0], 16, 27, false,
        sEmojisMap.put("1f468_1f3ff_200d_1f9b0", R.drawable.emoji_1f468_1f3ff_200d_1f9b0); // }, new String[0], 16, 28, false  sEmojisMap.put("1f468_200d_1f9b1", R.drawable.emoji_1f468_200d_1f9b1); // curly_haired_man
        sEmojisMap.put("1f468_1f3fb_200d_1f9b1", R.drawable.emoji_1f468_1f3fb_200d_1f9b1); // }, new String[0], 16, 30, false,
        sEmojisMap.put("1f468_1f3fc_200d_1f9b1", R.drawable.emoji_1f468_1f3fc_200d_1f9b1); // }, new String[0], 16, 31, false,
        sEmojisMap.put("1f468_1f3fd_200d_1f9b1", R.drawable.emoji_1f468_1f3fd_200d_1f9b1); // }, new String[0], 16, 32, false,
        sEmojisMap.put("1f468_1f3fe_200d_1f9b1", R.drawable.emoji_1f468_1f3fe_200d_1f9b1); // }, new String[0], 16, 33, false,
        sEmojisMap.put("1f468_1f3ff_200d_1f9b1", R.drawable.emoji_1f468_1f3ff_200d_1f9b1); // }, new String[0], 16, 34, false
        sEmojisMap.put("1f468_200d_1f9b3", R.drawable.emoji_1f468_200d_1f9b3); // white_haired_man
        sEmojisMap.put("1f468_1f3fb_200d_1f9b3", R.drawable.emoji_1f468_1f3fb_200d_1f9b3); // }, new String[0], 16, 42, false,
        sEmojisMap.put("1f468_1f3fc_200d_1f9b3", R.drawable.emoji_1f468_1f3fc_200d_1f9b3); // }, new String[0], 16, 43, false,
        sEmojisMap.put("1f468_1f3fd_200d_1f9b3", R.drawable.emoji_1f468_1f3fd_200d_1f9b3); // }, new String[0], 16, 44, false,
        sEmojisMap.put("1f468_1f3fe_200d_1f9b3", R.drawable.emoji_1f468_1f3fe_200d_1f9b3); // }, new String[0], 16, 45, false,
        sEmojisMap.put("1f468_1f3ff_200d_1f9b3", R.drawable.emoji_1f468_1f3ff_200d_1f9b3); // }, new String[0], 16, 46, false
        sEmojisMap.put("1f468_200d_1f9b2", R.drawable.emoji_1f468_200d_1f9b2); // bald_man
        sEmojisMap.put("1f468_1f3fb_200d_1f9b2", R.drawable.emoji_1f468_1f3fb_200d_1f9b2); // }, new String[0], 16, 36, false,
        sEmojisMap.put("1f468_1f3fc_200d_1f9b2", R.drawable.emoji_1f468_1f3fc_200d_1f9b2); // }, new String[0], 16, 37, false,
        sEmojisMap.put("1f468_1f3fd_200d_1f9b2", R.drawable.emoji_1f468_1f3fd_200d_1f9b2); // }, new String[0], 16, 38, false,
        sEmojisMap.put("1f468_1f3fe_200d_1f9b2", R.drawable.emoji_1f468_1f3fe_200d_1f9b2); // }, new String[0], 16, 39, false,
        sEmojisMap.put("1f468_1f3ff_200d_1f9b2", R.drawable.emoji_1f468_1f3ff_200d_1f9b2); // }, new String[0], 16, 40, false
        sEmojisMap.put("1f469", R.drawable.emoji_1f469); // woman
        sEmojisMap.put("1f469_1f3fb", R.drawable.emoji_1f469_1f3fb); // }, new String[0], 20, 10, false,
        sEmojisMap.put("1f469_1f3fc", R.drawable.emoji_1f469_1f3fc); // }, new String[0], 20, 11, false,
        sEmojisMap.put("1f469_1f3fd", R.drawable.emoji_1f469_1f3fd); // }, new String[0], 20, 12, false,
        sEmojisMap.put("1f469_1f3fe", R.drawable.emoji_1f469_1f3fe); // }, new String[0], 20, 13, false,
        sEmojisMap.put("1f469_1f3ff", R.drawable.emoji_1f469_1f3ff); // }, new String[0], 20, 14, false
        sEmojisMap.put("1f469_200d_1f9b0", R.drawable.emoji_1f469_200d_1f9b0); // red_haired_woman
        sEmojisMap.put("1f469_1f3fb_200d_1f9b0", R.drawable.emoji_1f469_1f3fb_200d_1f9b0); // }, new String[0], 19, 9, false,
        sEmojisMap.put("1f469_1f3fc_200d_1f9b0", R.drawable.emoji_1f469_1f3fc_200d_1f9b0); // }, new String[0], 19, 10, false,
        sEmojisMap.put("1f469_1f3fd_200d_1f9b0", R.drawable.emoji_1f469_1f3fd_200d_1f9b0); // }, new String[0], 19, 11, false,
        sEmojisMap.put("1f469_1f3fe_200d_1f9b0", R.drawable.emoji_1f469_1f3fe_200d_1f9b0); // }, new String[0], 19, 12, false,
        sEmojisMap.put("1f469_1f3ff_200d_1f9b0", R.drawable.emoji_1f469_1f3ff_200d_1f9b0); // }, new String[0], 19, 13, false
        sEmojisMap.put("1f469_200d_1f9b1", R.drawable.emoji_1f469_200d_1f9b1); // curly_haired_woman
        sEmojisMap.put("1f469_1f3fb_200d_1f9b1", R.drawable.emoji_1f469_1f3fb_200d_1f9b1); // }, new String[0], 19, 15, false,
        sEmojisMap.put("1f469_1f3fc_200d_1f9b1", R.drawable.emoji_1f469_1f3fc_200d_1f9b1); // }, new String[0], 19, 16, false,
        sEmojisMap.put("1f469_1f3fd_200d_1f9b1", R.drawable.emoji_1f469_1f3fd_200d_1f9b1); // }, new String[0], 19, 17, false,
        sEmojisMap.put("1f469_1f3fe_200d_1f9b1", R.drawable.emoji_1f469_1f3fe_200d_1f9b1); // }, new String[0], 19, 18, false,
        sEmojisMap.put("1f469_1f3ff_200d_1f9b1", R.drawable.emoji_1f469_1f3ff_200d_1f9b1); // }, new String[0], 19, 19, false
        sEmojisMap.put("1f469_200d_1f9b3", R.drawable.emoji_1f469_200d_1f9b3); // white_haired_woman
        sEmojisMap.put("1f469_1f3fb_200d_1f9b3", R.drawable.emoji_1f469_1f3fb_200d_1f9b3); // }, new String[0], 19, 27, false,
        sEmojisMap.put("1f469_1f3fc_200d_1f9b3", R.drawable.emoji_1f469_1f3fc_200d_1f9b3); // }, new String[0], 19, 28, false,
        sEmojisMap.put("1f469_1f3fd_200d_1f9b3", R.drawable.emoji_1f469_1f3fd_200d_1f9b3); // }, new String[0], 19, 29, false,
        sEmojisMap.put("1f469_1f3fe_200d_1f9b3", R.drawable.emoji_1f469_1f3fe_200d_1f9b3); // }, new String[0], 19, 30, false,
        sEmojisMap.put("1f469_1f3ff_200d_1f9b3", R.drawable.emoji_1f469_1f3ff_200d_1f9b3); // }, new String[0], 19, 31, false
        sEmojisMap.put("1f469_200d_1f9b2", R.drawable.emoji_1f469_200d_1f9b2); // bald_woman
        sEmojisMap.put("1f469_1f3fb_200d_1f9b2", R.drawable.emoji_1f469_1f3fb_200d_1f9b2); // }, new String[0], 19, 21, false,
        sEmojisMap.put("1f469_1f3fc_200d_1f9b2", R.drawable.emoji_1f469_1f3fc_200d_1f9b2); // }, new String[0], 19, 22, false,
        sEmojisMap.put("1f469_1f3fd_200d_1f9b2", R.drawable.emoji_1f469_1f3fd_200d_1f9b2); // }, new String[0], 19, 23, false,
        sEmojisMap.put("1f469_1f3fe_200d_1f9b2", R.drawable.emoji_1f469_1f3fe_200d_1f9b2); // }, new String[0], 19, 24, false,
        sEmojisMap.put("1f469_1f3ff_200d_1f9b2", R.drawable.emoji_1f469_1f3ff_200d_1f9b2); // }, new String[0], 19, 25, false
        sEmojisMap.put("1f471_200d_2640_fe0f", R.drawable.emoji_1f471_200d_2640_fe0f); // blond-haired-woman
        sEmojisMap.put("1f471_1f3fb_200d_2640_fe0f", R.drawable.emoji_1f471_1f3fb_200d_2640_fe0f); // }, new String[0], 22, 8, false,
        sEmojisMap.put("1f471_1f3fc_200d_2640_fe0f", R.drawable.emoji_1f471_1f3fc_200d_2640_fe0f); // }, new String[0], 22, 9, false,
        sEmojisMap.put("1f471_1f3fd_200d_2640_fe0f", R.drawable.emoji_1f471_1f3fd_200d_2640_fe0f); // }, new String[0], 22, 10, false,
        sEmojisMap.put("1f471_1f3fe_200d_2640_fe0f", R.drawable.emoji_1f471_1f3fe_200d_2640_fe0f); // }, new String[0], 22, 11, false,
        sEmojisMap.put("1f471_1f3ff_200d_2640_fe0f", R.drawable.emoji_1f471_1f3ff_200d_2640_fe0f); // }, new String[0], 22, 12, false
        sEmojisMap.put("1f471_200d_2642_fe0f", R.drawable.emoji_1f471_200d_2642_fe0f); // blond-haired-man
        sEmojisMap.put("1f471_1f3fb_200d_2642_fe0f", R.drawable.emoji_1f471_1f3fb_200d_2642_fe0f); // }, new String[0], 22, 14, false,
        sEmojisMap.put("1f471_1f3fc_200d_2642_fe0f", R.drawable.emoji_1f471_1f3fc_200d_2642_fe0f); // }, new String[0], 22, 15, false,
        sEmojisMap.put("1f471_1f3fd_200d_2642_fe0f", R.drawable.emoji_1f471_1f3fd_200d_2642_fe0f); // }, new String[0], 22, 16, false,
        sEmojisMap.put("1f471_1f3fe_200d_2642_fe0f", R.drawable.emoji_1f471_1f3fe_200d_2642_fe0f); // }, new String[0], 22, 17, false,
        sEmojisMap.put("1f471_1f3ff_200d_2642_fe0f", R.drawable.emoji_1f471_1f3ff_200d_2642_fe0f); // }, new String[0], 22, 18, false
        sEmojisMap.put("1f9d3", R.drawable.emoji_1f9d3); // older_adult
        sEmojisMap.put("1f9d3_1f3fb", R.drawable.emoji_1f9d3_1f3fb); // }, new String[0], 48, 23, false,
        sEmojisMap.put("1f9d3_1f3fc", R.drawable.emoji_1f9d3_1f3fc); // }, new String[0], 48, 24, false,
        sEmojisMap.put("1f9d3_1f3fd", R.drawable.emoji_1f9d3_1f3fd); // }, new String[0], 48, 25, false,
        sEmojisMap.put("1f9d3_1f3fe", R.drawable.emoji_1f9d3_1f3fe); // }, new String[0], 48, 26, false,
        sEmojisMap.put("1f9d3_1f3ff", R.drawable.emoji_1f9d3_1f3ff); // }, new String[0], 48, 27, false  sEmojisMap.put("1f474", R.drawable.emoji_1f474); // older_man
        sEmojisMap.put("1f474_1f3fb", R.drawable.emoji_1f474_1f3fb); // }, new String[0], 22, 50, false,
        sEmojisMap.put("1f474_1f3fc", R.drawable.emoji_1f474_1f3fc); // }, new String[0], 22, 51, false,
        sEmojisMap.put("1f474_1f3fd", R.drawable.emoji_1f474_1f3fd); // }, new String[0], 22, 52, false,
        sEmojisMap.put("1f474_1f3fe", R.drawable.emoji_1f474_1f3fe); // }, new String[0], 22, 53, false,
        sEmojisMap.put("1f474_1f3ff", R.drawable.emoji_1f474_1f3ff); // }, new String[0], 22, 54, false
        sEmojisMap.put("1f475", R.drawable.emoji_1f475); // older_woman
        sEmojisMap.put("1f475_1f3fb", R.drawable.emoji_1f475_1f3fb); // }, new String[0], 22, 56, false,
        sEmojisMap.put("1f475_1f3fc", R.drawable.emoji_1f475_1f3fc); // }, new String[0], 23, 0, false,
        sEmojisMap.put("1f475_1f3fd", R.drawable.emoji_1f475_1f3fd); // }, new String[0], 23, 1, false,
        sEmojisMap.put("1f475_1f3fe", R.drawable.emoji_1f475_1f3fe); // }, new String[0], 23, 2, false,
        sEmojisMap.put("1f475_1f3ff", R.drawable.emoji_1f475_1f3ff); // }, new String[0], 23, 3, false
        sEmojisMap.put("1f64d", R.drawable.emoji_1f64d); // person_frowning
        sEmojisMap.put("1f64d_1f3fb", R.drawable.emoji_1f64d_1f3fb); // }, new String[0], 33, 27, false,
        sEmojisMap.put("1f64d_1f3fc", R.drawable.emoji_1f64d_1f3fc); // }, new String[0], 33, 28, false,
        sEmojisMap.put("1f64d_1f3fd", R.drawable.emoji_1f64d_1f3fd); // }, new String[0], 33, 29, false,
        sEmojisMap.put("1f64d_1f3fe", R.drawable.emoji_1f64d_1f3fe); // }, new String[0], 33, 30, false,
        sEmojisMap.put("1f64d_1f3ff", R.drawable.emoji_1f64d_1f3ff); // }, new String[0], 33, 31, false
        sEmojisMap.put("1f64d_200d_2642_fe0f", R.drawable.emoji_1f64d_200d_2642_fe0f); // man-frowning
        sEmojisMap.put("1f64d_1f3fb_200d_2642_fe0f", R.drawable.emoji_1f64d_1f3fb_200d_2642_fe0f); // }, new String[0], 33, 21, false,
        sEmojisMap.put("1f64d_1f3fc_200d_2642_fe0f", R.drawable.emoji_1f64d_1f3fc_200d_2642_fe0f); // }, new String[0], 33, 22, false,
        sEmojisMap.put("1f64d_1f3fd_200d_2642_fe0f", R.drawable.emoji_1f64d_1f3fd_200d_2642_fe0f); // }, new String[0], 33, 23, false,
        sEmojisMap.put("1f64d_1f3fe_200d_2642_fe0f", R.drawable.emoji_1f64d_1f3fe_200d_2642_fe0f); // }, new String[0], 33, 24, false,
        sEmojisMap.put("1f64d_1f3ff_200d_2642_fe0f", R.drawable.emoji_1f64d_1f3ff_200d_2642_fe0f); // }, new String[0], 33, 25, false
        sEmojisMap.put("1f64d_200d_2640_fe0f", R.drawable.emoji_1f64d_200d_2640_fe0f); // woman-frowning
        sEmojisMap.put("1f64d_1f3fb_200d_2640_fe0f", R.drawable.emoji_1f64d_1f3fb_200d_2640_fe0f); // }, new String[0], 33, 15, false,
        sEmojisMap.put("1f64d_1f3fc_200d_2640_fe0f", R.drawable.emoji_1f64d_1f3fc_200d_2640_fe0f); // }, new String[0], 33, 16, false,
        sEmojisMap.put("1f64d_1f3fd_200d_2640_fe0f", R.drawable.emoji_1f64d_1f3fd_200d_2640_fe0f); // }, new String[0], 33, 17, false,
        sEmojisMap.put("1f64d_1f3fe_200d_2640_fe0f", R.drawable.emoji_1f64d_1f3fe_200d_2640_fe0f); // }, new String[0], 33, 18, false,
        sEmojisMap.put("1f64d_1f3ff_200d_2640_fe0f", R.drawable.emoji_1f64d_1f3ff_200d_2640_fe0f); // }, new String[0], 33, 19, false
        sEmojisMap.put("1f64e", R.drawable.emoji_1f64e); // person_with_pouting_face
        sEmojisMap.put("1f64e_1f3fb", R.drawable.emoji_1f64e_1f3fb); // }, new String[0], 33, 45, false,
        sEmojisMap.put("1f64e_1f3fc", R.drawable.emoji_1f64e_1f3fc); // }, new String[0], 33, 46, false,
        sEmojisMap.put("1f64e_1f3fd", R.drawable.emoji_1f64e_1f3fd); // }, new String[0], 33, 47, false,
        sEmojisMap.put("1f64e_1f3fe", R.drawable.emoji_1f64e_1f3fe); // }, new String[0], 33, 48, false,
        sEmojisMap.put("1f64e_1f3ff", R.drawable.emoji_1f64e_1f3ff); // }, new String[0], 33, 49, false
        sEmojisMap.put("1f64e_200d_2642_fe0f", R.drawable.emoji_1f64e_200d_2642_fe0f); // man-pouting
        sEmojisMap.put("1f64e_1f3fb_200d_2642_fe0f", R.drawable.emoji_1f64e_1f3fb_200d_2642_fe0f); // }, new String[0], 33, 39, false,
        sEmojisMap.put("1f64e_1f3fc_200d_2642_fe0f", R.drawable.emoji_1f64e_1f3fc_200d_2642_fe0f); // }, new String[0], 33, 40, false,
        sEmojisMap.put("1f64e_1f3fd_200d_2642_fe0f", R.drawable.emoji_1f64e_1f3fd_200d_2642_fe0f); // }, new String[0], 33, 41, false,
        sEmojisMap.put("1f64e_1f3fe_200d_2642_fe0f", R.drawable.emoji_1f64e_1f3fe_200d_2642_fe0f); // }, new String[0], 33, 42, false,
        sEmojisMap.put("1f64e_1f3ff_200d_2642_fe0f", R.drawable.emoji_1f64e_1f3ff_200d_2642_fe0f); // }, new String[0], 33, 43, false
        sEmojisMap.put("1f64e_200d_2640_fe0f", R.drawable.emoji_1f64e_200d_2640_fe0f); // woman-pouting
        sEmojisMap.put("1f64e_1f3fb_200d_2640_fe0f", R.drawable.emoji_1f64e_1f3fb_200d_2640_fe0f); // }, new String[0], 33, 33, false,
        sEmojisMap.put("1f64e_1f3fc_200d_2640_fe0f", R.drawable.emoji_1f64e_1f3fc_200d_2640_fe0f); // }, new String[0], 33, 34, false,
        sEmojisMap.put("1f64e_1f3fd_200d_2640_fe0f", R.drawable.emoji_1f64e_1f3fd_200d_2640_fe0f); // }, new String[0], 33, 35, false,
        sEmojisMap.put("1f64e_1f3fe_200d_2640_fe0f", R.drawable.emoji_1f64e_1f3fe_200d_2640_fe0f); // }, new String[0], 33, 36, false,
        sEmojisMap.put("1f64e_1f3ff_200d_2640_fe0f", R.drawable.emoji_1f64e_1f3ff_200d_2640_fe0f); // }, new String[0], 33, 37, false
        sEmojisMap.put("1f645", R.drawable.emoji_1f645); // no_good
        sEmojisMap.put("1f645_1f3fb", R.drawable.emoji_1f645_1f3fb); // }, new String[0], 32, 3, false,
        sEmojisMap.put("1f645_1f3fc", R.drawable.emoji_1f645_1f3fc); // }, new String[0], 32, 4, false,
        sEmojisMap.put("1f645_1f3fd", R.drawable.emoji_1f645_1f3fd); // }, new String[0], 32, 5, false,
        sEmojisMap.put("1f645_1f3fe", R.drawable.emoji_1f645_1f3fe); // }, new String[0], 32, 6, false,
        sEmojisMap.put("1f645_1f3ff", R.drawable.emoji_1f645_1f3ff); // }, new String[0], 32, 7, false
        sEmojisMap.put("1f645_200d_2642_fe0f", R.drawable.emoji_1f645_200d_2642_fe0f); // man-gesturing-no
        sEmojisMap.put("1f645_1f3fb_200d_2642_fe0f", R.drawable.emoji_1f645_1f3fb_200d_2642_fe0f); // }, new String[0], 31, 54, false,
        sEmojisMap.put("1f645_1f3fc_200d_2642_fe0f", R.drawable.emoji_1f645_1f3fc_200d_2642_fe0f); // }, new String[0], 31, 55, false,
        sEmojisMap.put("1f645_1f3fd_200d_2642_fe0f", R.drawable.emoji_1f645_1f3fd_200d_2642_fe0f); // }, new String[0], 31, 56, false,
        sEmojisMap.put("1f645_1f3fe_200d_2642_fe0f", R.drawable.emoji_1f645_1f3fe_200d_2642_fe0f); // }, new String[0], 32, 0, false,
        sEmojisMap.put("1f645_1f3ff_200d_2642_fe0f", R.drawable.emoji_1f645_1f3ff_200d_2642_fe0f); // }, new String[0], 32, 1, false
        sEmojisMap.put("1f645_200d_2640_fe0f", R.drawable.emoji_1f645_200d_2640_fe0f); // woman-gesturing-no
        sEmojisMap.put("1f645_1f3fb_200d_2640_fe0f", R.drawable.emoji_1f645_1f3fb_200d_2640_fe0f); // }, new String[0], 31, 48, false,
        sEmojisMap.put("1f645_1f3fc_200d_2640_fe0f", R.drawable.emoji_1f645_1f3fc_200d_2640_fe0f); // }, new String[0], 31, 49, false,
        sEmojisMap.put("1f645_1f3fd_200d_2640_fe0f", R.drawable.emoji_1f645_1f3fd_200d_2640_fe0f); // }, new String[0], 31, 50, false,
        sEmojisMap.put("1f645_1f3fe_200d_2640_fe0f", R.drawable.emoji_1f645_1f3fe_200d_2640_fe0f); // }, new String[0], 31, 51, false,
        sEmojisMap.put("1f645_1f3ff_200d_2640_fe0f", R.drawable.emoji_1f645_1f3ff_200d_2640_fe0f); // }, new String[0], 31, 52, false
 sEmojisMap.put("1f646", R.drawable.emoji_1f646); // ok_woman
        sEmojisMap.put("1f646_1f3fb", R.drawable.emoji_1f646_1f3fb); // }, new String[0], 32, 21, false,
        sEmojisMap.put("1f646_1f3fc", R.drawable.emoji_1f646_1f3fc); // }, new String[0], 32, 22, false,
        sEmojisMap.put("1f646_1f3fd", R.drawable.emoji_1f646_1f3fd); // }, new String[0], 32, 23, false,
        sEmojisMap.put("1f646_1f3fe", R.drawable.emoji_1f646_1f3fe); // }, new String[0], 32, 24, false,
        sEmojisMap.put("1f646_1f3ff", R.drawable.emoji_1f646_1f3ff); // }, new String[0], 32, 25, false
        sEmojisMap.put("1f646_200d_2642_fe0f", R.drawable.emoji_1f646_200d_2642_fe0f); // man-gesturing-ok
        sEmojisMap.put("1f646_1f3fb_200d_2642_fe0f", R.drawable.emoji_1f646_1f3fb_200d_2642_fe0f); // }, new String[0], 32, 15, false,
        sEmojisMap.put("1f646_1f3fc_200d_2642_fe0f", R.drawable.emoji_1f646_1f3fc_200d_2642_fe0f); // }, new String[0], 32, 16, false,
        sEmojisMap.put("1f646_1f3fd_200d_2642_fe0f", R.drawable.emoji_1f646_1f3fd_200d_2642_fe0f); // }, new String[0], 32, 17, false,
        sEmojisMap.put("1f646_1f3fe_200d_2642_fe0f", R.drawable.emoji_1f646_1f3fe_200d_2642_fe0f); // }, new String[0], 32, 18, false,
        sEmojisMap.put("1f646_1f3ff_200d_2642_fe0f", R.drawable.emoji_1f646_1f3ff_200d_2642_fe0f); // }, new String[0], 32, 19, false
        sEmojisMap.put("1f646_200d_2640_fe0f", R.drawable.emoji_1f646_200d_2640_fe0f); // woman-gesturing-ok
        sEmojisMap.put("1f646_1f3fb_200d_2640_fe0f", R.drawable.emoji_1f646_1f3fb_200d_2640_fe0f); // }, new String[0], 32, 9, false,
        sEmojisMap.put("1f646_1f3fc_200d_2640_fe0f", R.drawable.emoji_1f646_1f3fc_200d_2640_fe0f); // }, new String[0], 32, 10, false,
        sEmojisMap.put("1f646_1f3fd_200d_2640_fe0f", R.drawable.emoji_1f646_1f3fd_200d_2640_fe0f); // }, new String[0], 32, 11, false,
        sEmojisMap.put("1f646_1f3fe_200d_2640_fe0f", R.drawable.emoji_1f646_1f3fe_200d_2640_fe0f); // }, new String[0], 32, 12, false,
        sEmojisMap.put("1f646_1f3ff_200d_2640_fe0f", R.drawable.emoji_1f646_1f3ff_200d_2640_fe0f); // }, new String[0], 32, 13, false
        sEmojisMap.put("1f481", R.drawable.emoji_1f481); // information_desk_person
        sEmojisMap.put("1f481_1f3fb", R.drawable.emoji_1f481_1f3fb); // }, new String[0], 24, 3, false,
        sEmojisMap.put("1f481_1f3fc", R.drawable.emoji_1f481_1f3fc); // }, new String[0], 24, 4, false,
        sEmojisMap.put("1f481_1f3fd", R.drawable.emoji_1f481_1f3fd); // }, new String[0], 24, 5, false,
        sEmojisMap.put("1f481_1f3fe", R.drawable.emoji_1f481_1f3fe); // }, new String[0], 24, 6, false,
        sEmojisMap.put("1f481_1f3ff", R.drawable.emoji_1f481_1f3ff); // }, new String[0], 24, 7, false
        sEmojisMap.put("1f481_200d_2642_fe0f", R.drawable.emoji_1f481_200d_2642_fe0f); // man-tipping-hand
        sEmojisMap.put("1f481_1f3fb_200d_2642_fe0f", R.drawable.emoji_1f481_1f3fb_200d_2642_fe0f); // }, new String[0], 23, 54, false,
        sEmojisMap.put("1f481_1f3fc_200d_2642_fe0f", R.drawable.emoji_1f481_1f3fc_200d_2642_fe0f); // }, new String[0], 23, 55, false,
        sEmojisMap.put("1f481_1f3fd_200d_2642_fe0f", R.drawable.emoji_1f481_1f3fd_200d_2642_fe0f); // }, new String[0], 23, 56, false,
        sEmojisMap.put("1f481_1f3fe_200d_2642_fe0f", R.drawable.emoji_1f481_1f3fe_200d_2642_fe0f); // }, new String[0], 24, 0, false,
        sEmojisMap.put("1f481_1f3ff_200d_2642_fe0f", R.drawable.emoji_1f481_1f3ff_200d_2642_fe0f); // }, new String[0], 24, 1, false
        sEmojisMap.put("1f481_200d_2640_fe0f", R.drawable.emoji_1f481_200d_2640_fe0f); // woman-tipping-hand
        sEmojisMap.put("1f481_1f3fb_200d_2640_fe0f", R.drawable.emoji_1f481_1f3fb_200d_2640_fe0f); // }, new String[0], 23, 48, false,
        sEmojisMap.put("1f481_1f3fc_200d_2640_fe0f", R.drawable.emoji_1f481_1f3fc_200d_2640_fe0f); // }, new String[0], 23, 49, false,
        sEmojisMap.put("1f481_1f3fd_200d_2640_fe0f", R.drawable.emoji_1f481_1f3fd_200d_2640_fe0f); // }, new String[0], 23, 50, false,
        sEmojisMap.put("1f481_1f3fe_200d_2640_fe0f", R.drawable.emoji_1f481_1f3fe_200d_2640_fe0f); // }, new String[0], 23, 51, false,
        sEmojisMap.put("1f481_1f3ff_200d_2640_fe0f", R.drawable.emoji_1f481_1f3ff_200d_2640_fe0f); // }, new String[0], 23, 52, false
        sEmojisMap.put("1f64b", R.drawable.emoji_1f64b); // raising_hand
        sEmojisMap.put("1f64b_1f3fb", R.drawable.emoji_1f64b_1f3fb); // }, new String[0], 33, 3, false,
        sEmojisMap.put("1f64b_1f3fc", R.drawable.emoji_1f64b_1f3fc); // }, new String[0], 33, 4, false,
        sEmojisMap.put("1f64b_1f3fd", R.drawable.emoji_1f64b_1f3fd); // }, new String[0], 33, 5, false,
        sEmojisMap.put("1f64b_1f3fe", R.drawable.emoji_1f64b_1f3fe); // }, new String[0], 33, 6, false,
        sEmojisMap.put("1f64b_1f3ff", R.drawable.emoji_1f64b_1f3ff); // }, new String[0], 33, 7, false
        sEmojisMap.put("1f64b_200d_2642_fe0f", R.drawable.emoji_1f64b_200d_2642_fe0f); // man-raising-hand
        sEmojisMap.put("1f64b_1f3fb_200d_2642_fe0f", R.drawable.emoji_1f64b_1f3fb_200d_2642_fe0f); // }, new String[0], 32, 54, false,
        sEmojisMap.put("1f64b_1f3fc_200d_2642_fe0f", R.drawable.emoji_1f64b_1f3fc_200d_2642_fe0f); // }, new String[0], 32, 55, false,
        sEmojisMap.put("1f64b_1f3fd_200d_2642_fe0f", R.drawable.emoji_1f64b_1f3fd_200d_2642_fe0f); // }, new String[0], 32, 56, false,
        sEmojisMap.put("1f64b_1f3fe_200d_2642_fe0f", R.drawable.emoji_1f64b_1f3fe_200d_2642_fe0f); // }, new String[0], 33, 0, false,
        sEmojisMap.put("1f64b_1f3ff_200d_2642_fe0f", R.drawable.emoji_1f64b_1f3ff_200d_2642_fe0f); // }, new String[0], 33, 1, false
        sEmojisMap.put("1f64b_200d_2640_fe0f", R.drawable.emoji_1f64b_200d_2640_fe0f); // woman-raising-hand
        sEmojisMap.put("1f64b_1f3fb_200d_2640_fe0f", R.drawable.emoji_1f64b_1f3fb_200d_2640_fe0f); // }, new String[0], 32, 48, false,
        sEmojisMap.put("1f64b_1f3fc_200d_2640_fe0f", R.drawable.emoji_1f64b_1f3fc_200d_2640_fe0f); // }, new String[0], 32, 49, false,
        sEmojisMap.put("1f64b_1f3fd_200d_2640_fe0f", R.drawable.emoji_1f64b_1f3fd_200d_2640_fe0f); // }, new String[0], 32, 50, false,
        sEmojisMap.put("1f64b_1f3fe_200d_2640_fe0f", R.drawable.emoji_1f64b_1f3fe_200d_2640_fe0f); // }, new String[0], 32, 51, false,
        sEmojisMap.put("1f64b_1f3ff_200d_2640_fe0f", R.drawable.emoji_1f64b_1f3ff_200d_2640_fe0f); // }, new String[0], 32, 52, false
        sEmojisMap.put("1f9cf", R.drawable.emoji_1f9cf); // deaf_person
        sEmojisMap.put("1f9cf_1f3fb", R.drawable.emoji_1f9cf_1f3fb); // }, new String[0], 45, 11, false,
        sEmojisMap.put("1f9cf_1f3fc", R.drawable.emoji_1f9cf_1f3fc); // }, new String[0], 45, 12, false,
        sEmojisMap.put("1f9cf_1f3fd", R.drawable.emoji_1f9cf_1f3fd); // }, new String[0], 45, 13, false,
        sEmojisMap.put("1f9cf_1f3fe", R.drawable.emoji_1f9cf_1f3fe); // }, new String[0], 45, 14, false,
        sEmojisMap.put("1f9cf_1f3ff", R.drawable.emoji_1f9cf_1f3ff); // }, new String[0], 45, 15, false
        sEmojisMap.put("1f9cf_20 _fe0f", R.drawable.emoji_1f9cf_200d_2642_fe0f); // deaf_man
        sEmojisMap.put("1f9cf_1f3fb_200d_2642_fe0f", R.drawable.emoji_1f9cf_1f3fb_200d_2642_fe0f); // }, new String[0], 45, 5, false,
        sEmojisMap.put("1f9cf_1f3fc_200d_2642_fe0f", R.drawable.emoji_1f9cf_1f3fc_200d_2642_fe0f); // }, new String[0], 45, 6, false,
        sEmojisMap.put("1f9cf_1f3fd_200d_2642_fe0f", R.drawable.emoji_1f9cf_1f3fd_200d_2642_fe0f); // }, new String[0], 45, 7, false,
        sEmojisMap.put("1f9cf_1f3fe_200d_2642_fe0f", R.drawable.emoji_1f9cf_1f3fe_200d_2642_fe0f); // }, new String[0], 45, 8, false,
        sEmojisMap.put("1f9cf_1f3ff_200d_2642_fe0f", R.drawable.emoji_1f9cf_1f3ff_200d_2642_fe0f); // }, new String[0], 45, 9, false
        sEmojisMap.put("1f9cf_200d_2640_fe0f", R.drawable.emoji_1f9cf_200d_2640_fe0f); // deaf_woman
        sEmojisMap.put("1f9cf_1f3fb_200d_2640_fe0f", R.drawable.emoji_1f9cf_1f3fb_200d_2640_fe0f); // }, new String[0], 44, 56, false,
        sEmojisMap.put("1f9cf_1f3fc_200d_2640_fe0f", R.drawable.emoji_1f9cf_1f3fc_200d_2640_fe0f); // }, new String[0], 45, 0, false,
        sEmojisMap.put("1f9cf_1f3fd_200d_2640_fe0f", R.drawable.emoji_1f9cf_1f3fd_200d_2640_fe0f); // }, new String[0], 45, 1, false,
        sEmojisMap.put("1f9cf_1f3fe_200d_2640_fe0f", R.drawable.emoji_1f9cf_1f3fe_200d_2640_fe0f); // }, new String[0], 45, 2, false,
        sEmojisMap.put("1f9cf_1f3ff_200d_2640_fe0f", R.drawable.emoji_1f9cf_1f3ff_200d_2640_fe0f); // }, new String[0], 45, 3, false
        sEmojisMap.put("1f647", R.drawable.emoji_1f647); // bow
        sEmojisMap.put("1f647_1f3fb", R.drawable.emoji_1f647_1f3fb); // }, new String[0], 32, 39, false,
        sEmojisMap.put("1f647_1f3fc", R.drawable.emoji_1f647_1f3fc); // }, new String[0], 32, 40, false,
        sEmojisMap.put("1f647_1f3fd", R.drawable.emoji_1f647_1f3fd); // }, new String[0], 32, 41, false,
        sEmojisMap.put("1f647_1f3fe", R.drawable.emoji_1f647_1f3fe); // }, new String[0], 32, 42, false,
        sEmojisMap.put("1f647_1f3ff", R.drawable.emoji_1f647_1f3ff); // }, new String[0], 32, 43, false
        sEmojisMap.put("1f647_200d_2642_fe0f", R.drawable.emoji_1f647_200d_2642_fe0f); // man-bowing
        sEmojisMap.put("1f647_1f3fb_200d_2642_fe0f", R.drawable.emoji_1f647_1f3fb_200d_2642_fe0f); // }, new String[0], 32, 33, false,
        sEmojisMap.put("1f647_1f3fc_200d_2642_fe0f", R.drawable.emoji_1f647_1f3fc_200d_2642_fe0f); // }, new String[0], 32, 34, false,
        sEmojisMap.put("1f647_1f3fd_200d_2642_fe0f", R.drawable.emoji_1f647_1f3fd_200d_2642_fe0f); // }, new String[0], 32, 35, false,
        sEmojisMap.put("1f647_1f3fe_200d_2642_fe0f", R.drawable.emoji_1f647_1f3fe_200d_2642_fe0f); // }, new String[0], 32, 36, false,
        sEmojisMap.put("1f647_1f3ff_200d_2642_fe0f", R.drawable.emoji_1f647_1f3ff_200d_2642_fe0f); // }, new String[0], 32, 37, false
        sEmojisMap.put("1f647_200d_2640_fe0f", R.drawable.emoji_1f647_200d_2640_fe0f); // woman-bowing
        sEmojisMap.put("1f647_1f3fb_200d_2640_fe0f", R.drawable.emoji_1f647_1f3fb_200d_2640_fe0f); // }, new String[0], 32, 27, false,
        sEmojisMap.put("1f647_1f3fc_200d_2640_fe0f", R.drawable.emoji_1f647_1f3fc_200d_2640_fe0f); // }, new String[0], 32, 28, false,
        sEmojisMap.put("1f647_1f3fd_200d_2640_fe0f", R.drawable.emoji_1f647_1f3fd_200d_2640_fe0f); // }, new String[0], 32, 29, false,
        sEmojisMap.put("1f647_1f3fe_200d_2640_fe0f", R.drawable.emoji_1f647_1f3fe_200d_2640_fe0f); // }, new String[0], 32, 30, false,
        sEmojisMap.put("1f647_1f3ff_200d_2640_fe0f", R.drawable.emoji_1f647_1f3ff_200d_2640_fe0f); // }, new String[0], 32, 31, false
        sEmojisMap.put("1f926", R.drawable.emoji_1f926); // face_palm
        sEmojisMap.put("1f926_1f3fb", R.drawable.emoji_1f926_1f3fb); // }, new String[0], 38, 36, false,
        sEmojisMap.put("1f926_1f3fc", R.drawable.emoji_1f926_1f3fc); // }, new String[0], 38, 37, false,
        sEmojisMap.put("1f926_1f3fd", R.drawable.emoji_1f926_1f3fd); // }, new String[0], 38, 38, false,
        sEmojisMap.put("1f926_1f3fe", R.drawable.emoji_1f926_1f3fe); // }, new String[0], 38, 39, false,
        sEmojisMap.put("1f926_1f3ff", R.drawable.emoji_1f926_1f3ff); // }, new String[0], 38, 40, false
        sEmojisMap.put("1f926_200d_2642_fe0f", R.drawable.emoji_1f926_200d_2642_fe0f); // man-facepalming
        sEmojisMap.put("1f926_1f3fb_200d_2642_fe0f", R.drawable.emoji_1f926_1f3fb_200d_2642_fe0f); // }, new String[0], 38, 30, false,
        sEmojisMap.put("1f926_1f3fc_200d_2642_fe0f", R.drawable.emoji_1f926_1f3fc_200d_2642_fe0f); // }, new String[0], 38, 31, false,
        sEmojisMap.put("1f926_1f3fd_200d_2642_fe0f", R.drawable.emoji_1f926_1f3fd_200d_2642_fe0f); // }, new String[0], 38, 32, false,
        sEmojisMap.put("1f926_1f3fe_200d_2642_fe0f", R.drawable.emoji_1f926_1f3fe_200d_2642_fe0f); // }, new String[0], 38, 33, false,
        sEmojisMap.put("1f926_1f3ff_200d_2642_fe0f", R.drawable.emoji_1f926_1f3ff_200d_2642_fe0f); // }, new String[0], 38, 34, false
        sEmojisMap.put("1f926_200d_2640_fe0f", R.drawable.emoji_1f926_200d_2640_fe0f); // woman-facepalming
        sEmojisMap.put("1f926_1f3fb_200d_2640_fe0f", R.drawable.emoji_1f926_1f3fb_200d_2640_fe0f); // }, new String[0], 38, 24, false,
        sEmojisMap.put("1f926_1f3fc_200d_2640_fe0f", R.drawable.emoji_1f926_1f3fc_200d_2640_fe0f); // }, new String[0], 38, 25, false,
        sEmojisMap.put("1f926_1f3fd_200d_2640_fe0f", R.drawable.emoji_1f926_1f3fd_200d_2640_fe0f); // }, new String[0], 38, 26, false,
        sEmojisMap.put("1f926_1f3fe_200d_2640_fe0f", R.drawable.emoji_1f926_1f3fe_200d_2640_fe0f); // }, new String[0], 38, 27, false,
        sEmojisMap.put("1f926_1f3ff_200d_2640_fe0f", R.drawable.emoji_1f926_1f3ff_200d_2640_fe0f); // }, new String[0], 38, 28, false
        sEmojisMap.put("1f937", R.drawable.emoji_1f937); // shrug
        sEmojisMap.put("1f937_1f3fb", R.drawable.emoji_1f937_1f3fb); // }, new String[0], 39, 48, false,
        sEmojisMap.put("1f937_1f3fc", R.drawable.emoji_1f937_1f3fc); // }, new String[0], 39, 49, false,
        sEmojisMap.put("1f937_1f3fd", R.drawable.emoji_1f937_1f3fd); // }, new String[0], 39, 50, false,
        sEmojisMap.put("1f937_1f3fe", R.drawable.emoji_1f937_1f3fe); // }, new String[0], 39, 51, false,
        sEmojisMap.put("1f937_1f3ff", R.drawable.emoji_1f937_1f3ff); // }, new String[0], 39, 52, false
        sEmojisMap.put("1f937_200d_2642_fe0f", R.drawable.emoji_1f937_200d_2642_fe0f); // man-shrugging
        sEmojisMap.put("1f937_1f3fb_200d_2642_fe0f", R.drawable.emoji_1f937_1f3fb_200d_2642_fe0f); // }, new String[0], 39, 42, false,
        sEmojisMap.put("1f937_1f3fc_200d_2642_fe0f", R.drawable.emoji_1f937_1f3fc_200d_2642_fe0f); // }, new String[0], 39, 43, false,
        sEmojisMap.put("1f937_1f3fd_200d_2642_fe0f", R.drawable.emoji_1f937_1f3fd_200d_2642_fe0f); // }, new String[0], 39, 44, false,
        sEmojisMap.put("1f937_1f3fe_200d_2642_fe0f", R.drawable.emoji_1f937_1f3fe_200d_2642_fe0f); // }, new String[0], 39, 45, false,
        sEmojisMap.put("1f937_1f3ff_200d_2642_fe0f", R.drawable.emoji_1f937_1f3ff_200d_2642_fe0f); // }, new String[0], 39, 46, false
        sEmojisMap.put("1f937_200d_2640_fe0f", R.drawable.emoji_1f937_200d_2640_fe0f); // woman-shrugging
        sEmojisMap.put("1f937_1f3fb_200d_2640_fe0f", R.drawable.emoji_1f937_1f3fb_200d_2640_fe0f); // }, new String[0], 39, 36, false,
        sEmojisMap.put("1f937_1f3fc_200d_2640_fe0f", R.drawable.emoji_1f937_1f3fc_200d_2640_fe0f); // }, new String[0], 39, 37, false,
        sEmojisMap.put("1f937_1f3fd_200d_2640_fe0f", R.drawable.emoji_1f937_1f3fd_200d_2640_fe0f); // }, new String[0], 39, 38, false,
        sEmojisMap.put("1f937_1f3fe_200d_2640_fe0f", R.drawable.emoji_1f937_1f3fe_200d_2640_fe0f); // }, new String[0], 39, 39, false,
        sEmojisMap.put("1f937_1f3ff_200d_2640_fe0f", R.drawable.emoji_1f937_1f3ff_200d_2640_fe0f); // }, new String[0], 39, 40, false
        sEmojisMap.put("1f4_2695_fe0f", R.drawable.emoji_1f468_200d_2695_fe0f); // male-doctor
        sEmojisMap.put("1f468_1f3fb_200d_2695_fe0f", R.drawable.emoji_1f468_1f3fb_200d_2695_fe0f); // }, new String[0], 17, 3, false,
        sEmojisMap.put("1f468_1f3fc_200d_2695_fe0f", R.drawable.emoji_1f468_1f3fc_200d_2695_fe0f); // }, new String[0], 17, 4, false,
        sEmojisMap.put("1f468_1f3fd_200d_2695_fe0f", R.drawable.emoji_1f468_1f3fd_200d_2695_fe0f); // }, new String[0], 17, 5, false,
        sEmojisMap.put("1f468_1f3fe_200d_2695_fe0f", R.drawable.emoji_1f468_1f3fe_200d_2695_fe0f); // }, new String[0], 17, 6, false,
        sEmojisMap.put("1f468_1f3ff_200d_2695_fe0f", R.drawable.emoji_1f468_1f3ff_200d_2695_fe0f); // }, new String[0], 17, 7, false
        sEmojisMap.put("1f469_200d_2695_fe0f", R.drawable.emoji_1f469_200d_2695_fe0f); // female-doctor
        sEmojisMap.put("1f469_1f3fb_200d_2695_fe0f", R.drawable.emoji_1f469_1f3fb_200d_2695_fe0f); // }, new String[0], 19, 45, false,
        sEmojisMap.put("1f469_1f3fc_200d_2695_fe0f", R.drawable.emoji_1f469_1f3fc_200d_2695_fe0f); // }, new String[0], 19, 46, false,
        sEmojisMap.put("1f469_1f3fd_200d_2695_fe0f", R.drawable.emoji_1f469_1f3fd_200d_2695_fe0f); // }, new String[0], 19, 47, false,
        sEmojisMap.put("1f469_1f3fe_200d_2695_fe0f", R.drawable.emoji_1f469_1f3fe_200d_2695_fe0f); // }, new String[0], 19, 48, false,
        sEmojisMap.put("1f469_1f3ff_200d_2695_fe0f", R.drawable.emoji_1f469_1f3ff_200d_2695_fe0f); // }, new String[0], 19, 49, false
        sEmojisMap.put("1f468_200d_1f393", R.drawable.emoji_1f468_200d_1f393); // male-student
        sEmojisMap.put("1f468_1f3fb_200d_1f393", R.drawable.emoji_1f468_1f3fb_200d_1f393); // }, new String[0], 14, 51, false,
        sEmojisMap.put("1f468_1f3fc_200d_1f393", R.drawable.emoji_1f468_1f3fc_200d_1f393); // }, new String[0], 14, 52, false,
        sEmojisMap.put("1f468_1f3fd_200d_1f393", R.drawable.emoji_1f468_1f3fd_200d_1f393); // }, new String[0], 14, 53, false,
        sEmojisMap.put("1f468_1f3fe_200d_1f393", R.drawable.emoji_1f468_1f3fe_200d_1f393); // }, new String[0], 14, 54, false,
        sEmojisMap.put("1f468_1f3ff_200d_1f393", R.drawable.emoji_1f468_1f3ff_200d_1f393); // }, new String[0], 14, 55, false
        sEmojisMap.put("1f469_200d_1f393", R.drawable.emoji_1f469_200d_1f393); // female-student
        sEmojisMap.put("1f469_1f3fb_200d_1f393", R.drawable.emoji_1f469_1f3fb_200d_1f393); // }, new String[0], 17, 41, false,
        sEmojisMap.put("1f469_1f3fc_200d_1f393", R.drawable.emoji_1f469_1f3fc_200d_1f393); // }, new String[0], 17, 42, false,
        sEmojisMap.put("1f469_1f3fd_200d_1f393", R.drawable.emoji_1f469_1f3fd_200d_1f393); // }, new String[0], 17, 43, false,
        sEmojisMap.put("1f469_1f3fe_200d_1f393", R.drawable.emoji_1f469_1f3fe_200d_1f393); // }, new String[0], 17, 44, false,
        sEmojisMap.put("1f469_1f3ff_200d_1f393", R.drawable.emoji_1f469_1f3ff_200d_1f393); // }, new String[0], 17, 45, false
        sEmojisMap.put("1f468_200d_1f3e", R.drawable.emoji_1f468_200d_1f3eb); // male-teacher
        sEmojisMap.put("1f468_1f3fb_200d_1f3eb", R.drawable.emoji_1f468_1f3fb_200d_1f3eb); // }, new String[0], 15, 12, false,
        sEmojisMap.put("1f468_1f3fc_200d_1f3eb", R.drawable.emoji_1f468_1f3fc_200d_1f3eb); // }, new String[0], 15, 13, false,
        sEmojisMap.put("1f468_1f3fd_200d_1f3eb", R.drawable.emoji_1f468_1f3fd_200d_1f3eb); // }, new String[0], 15, 14, false,
        sEmojisMap.put("1f468_1f3fe_200d_1f3eb", R.drawable.emoji_1f468_1f3fe_200d_1f3eb); // }, new String[0], 15, 15, false,
        sEmojisMap.put("1f468_1f3ff_200d_1f3eb", R.drawable.emoji_1f468_1f3ff_200d_1f3eb); // }, new String[0], 15, 16, false
        sEmojisMap.put("1f469_200d_1f3eb", R.drawable.emoji_1f469_200d_1f3eb); // female-teacher
        sEmojisMap.put("1f469_1f3fb_200d_1f3eb", R.drawable.emoji_1f469_1f3fb_200d_1f3eb); // }, new String[0], 18, 2, false,
        sEmojisMap.put("1f469_1f3fc_200d_1f3eb", R.drawable.emoji_1f469_1f3fc_200d_1f3eb); // }, new String[0], 18, 3, false,
        sEmojisMap.put("1f469_1f3fd_200d_1f3eb", R.drawable.emoji_1f469_1f3fd_200d_1f3eb); // }, new String[0], 18, 4, false,
        sEmojisMap.put("1f469_1f3fe_200d_1f3eb", R.drawable.emoji_1f469_1f3fe_200d_1f3eb); // }, new String[0], 18, 5, false,
        sEmojisMap.put("1f469_1f3ff_200d_1f3eb", R.drawable.emoji_1f469_1f3ff_200d_1f3eb); // }, new String[0], 18, 6, false
        sEmojisMap.put("1f468_200d_2696_fe0f", R.drawable.emoji_1f468_200d_2696_fe0f); // male-judge
        sEmojisMap.put("1f468_1f3fb_200d_2696_fe0f", R.drawable.emoji_1f468_1f3fb_200d_2696_fe0f); // }, new String[0], 17, 9, false,
        sEmojisMap.put("1f468_1f3fc_200d_2696_fe0f", R.drawable.emoji_1f468_1f3fc_200d_2696_fe0f); // }, new String[0], 17, 10, false,
        sEmojisMap.put("1f468_1f3fd_200d_2696_fe0f", R.drawable.emoji_1f468_1f3fd_200d_2696_fe0f); // }, new String[0], 17, 11, false,
        sEmojisMap.put("1f468_1f3fe_200d_2696_fe0f", R.drawable.emoji_1f468_1f3fe_200d_2696_fe0f); // }, new String[0], 17, 12, false,
        sEmojisMap.put("1f468_1f3ff_200d_2696_fe0f", R.drawable.emoji_1f468_1f3ff_200d_2696_fe0f); // }, new String[0], 17, 13, false
        sEmojisMap.put("1f469_200d_2696_fe0f", R.drawable.emoji_1f469_200d_2696_fe0f); // female-judge
        sEmojisMap.put("1f469_1f3fb_200d_2696_fe0f", R.drawable.emoji_1f469_1f3fb_200d_2696_fe0f); // }, new String[0], 19, 51, false,
        sEmojisMap.put("1f469_1f3fc_200d_2696_fe0f", R.drawable.emoji_1f469_1f3fc_200d_2696_fe0f); // }, new String[0], 19, 52, false,
        sEmojisMap.put("1f469_1f3fd_200d_2696_fe0f", R.drawable.emoji_1f469_1f3fd_200d_2696_fe0f); // }, new String[0], 19, 53, false,
        sEmojisMap.put("1f469_1f3fe_200d_2696_fe0f", R.drawable.emoji_1f469_1f3fe_200d_2696_fe0f); // }, new String[0], 19, 54, false,
        sEmojisMap.put("1f469_1f3ff_200d_2696_fe0f", R.drawable.emoji_1f469_1f3ff_200d_2696_fe0f); // }, new String[0], 19, 55, false
        sEmojisMap.put("1f468_200d_1f33e", R.drawable.emoji_1f468_200d_1f33e); // male-farmer
        sEmojisMap.put("1f468_1f3fb_200d_1f33e", R.drawable.emoji_1f468_1f3fb_200d_1f33e); // }, new String[0], 14, 39, false,
        sEmojisMap.put("1f468_1f3fc_200d_1f33e", R.drawable.emoji_1f468_1f3fc_200d_1f33e); // }, new String[0], 14, 40, false,
        sEmojisMap.put("1f468_1f3fd_200d_1f33e", R.drawable.emoji_1f468_1f3fd_200d_1f33e); // }, new String[0], 14, 41, false,
        sEmojisMap.put("1f468_1f3fe_200d_1f33e", R.drawable.emoji_1f468_1f3fe_200d_1f33e); // }, new String[0], 14, 42, false,
        sEmojisMap.put("1f468_1f3ff_200d_1f33e", R.drawable.emoji_1f468_1f3ff_200d_1f33e); // }, new String[0], 14, 43, false
 sEmojisMap.put("1f469_200d_1f33e", R.drawable.emoji_1f469_200d_1f33e); // female-farmer
        sEmojisMap.put("1f469_1f3fb_200d_1f33e", R.drawable.emoji_1f469_1f3fb_200d_1f33e); // }, new String[0], 17, 29, false,
        sEmojisMap.put("1f469_1f3fc_200d_1f33e", R.drawable.emoji_1f469_1f3fc_200d_1f33e); // }, new String[0], 17, 30, false,
        sEmojisMap.put("1f469_1f3fd_200d_1f33e", R.drawable.emoji_1f469_1f3fd_200d_1f33e); // }, new String[0], 17, 31, false,
        sEmojisMap.put("1f469_1f3fe_200d_1f33e", R.drawable.emoji_1f469_1f3fe_200d_1f33e); // }, new String[0], 17, 32, false,
        sEmojisMap.put("1f469_1f3ff_200d_1f33e", R.drawable.emoji_1f469_1f3ff_200d_1f33e); // }, new String[0], 17, 33, false
        sEmojisMap.put("1f468_200d_1f373", R.drawable.emoji_1f468_200d_1f373); // male-cook
        sEmojisMap.put("1f468_1f3fb_200d_1f373", R.drawable.emoji_1f468_1f3fb_200d_1f373); // }, new String[0], 14, 45, false,
        sEmojisMap.put("1f468_1f3fc_200d_1f373", R.drawable.emoji_1f468_1f3fc_200d_1f373); // }, new String[0], 14, 46, false,
        sEmojisMap.put("1f468_1f3fd_200d_1f373", R.drawable.emoji_1f468_1f3fd_200d_1f373); // }, new String[0], 14, 47, false,
        sEmojisMap.put("1f468_1f3fe_200d_1f373", R.drawable.emoji_1f468_1f3fe_200d_1f373); // }, new String[0], 14, 48, false,
        sEmojisMap.put("1f468_1f3ff_200d_1f373", R.drawable.emoji_1f468_1f3ff_200d_1f373); // }, new String[0], 14, 49, false
        sEmojisMap.put("1f469_200d_1f373", R.drawable.emoji_1f469_200d_1f373); // female-cook
        sEmojisMap.put("1f469_1f3fb_200d_1f373", R.drawable.emoji_1f469_1f3fb_200d_1f373); // }, new String[0], 17, 35, false,
        sEmojisMap.put("1f469_1f3fc_200d_1f373", R.drawable.emoji_1f469_1f3fc_200d_1f373); // }, new String[0], 17, 36, false,
        sEmojisMap.put("1f469_1f3fd_200d_1f373", R.drawable.emoji_1f469_1f3fd_200d_1f373); // }, new String[0], 17, 37, false,
        sEmojisMap.put("1f469_1f3fe_200d_1f373", R.drawable.emoji_1f469_1f3fe_200d_1f373); // }, new String[0], 17, 38, false,
        sEmojisMap.put("1f469_1f3ff_200d_1f373", R.drawable.emoji_1f469_1f3ff_200d_1f373); // }, new String[0], 17, 39, false
        sEmojisMap.put("1f468_200d_1f527", R.drawable.emoji_1f468_200d_1f527); // male-mechanic
        sEmojisMap.put("1f468_1f3fb_200d_1f527", R.drawable.emoji_1f468_1f3fb_200d_1f527); // }, new String[0], 15, 51, false,
        sEmojisMap.put("1f468_1f3fc_200d_1f527", R.drawable.emoji_1f468_1f3fc_200d_1f527); // }, new String[0], 15, 52, false,
        sEmojisMap.put("1f468_1f3fd_200d_1f527", R.drawable.emoji_1f468_1f3fd_200d_1f527); // }, new String[0], 15, 53, false,
        sEmojisMap.put("1f468_1f3fe_200d_1f527", R.drawable.emoji_1f468_1f3fe_200d_1f527); // }, new String[0], 15, 54, false,
        sEmojisMap.put("1f468_1f3ff_200d_1f527", R.drawable.emoji_1f468_1f3ff_200d_1f527); // }, new String[0], 15, 55, false
        sEmojisMap.put("1f469_200d_1f527", R.drawable.emoji_1f469_200d_1f527); // female-mechanic
        sEmojisMap.put("1f469_1f3fb_200d_1f527", R.drawable.emoji_1f469_1f3fb_200d_1f527); // }, new String[0], 18, 36, false,
        sEmojisMap.put("1f469_1f3fc_200d_1f527", R.drawable.emoji_1f469_1f3fc_200d_1f527); // }, new String[0], 18, 37, false,
        sEmojisMap.put("1f469_1f3fd_200d_1f527", R.drawable.emoji_1f469_1f3fd_200d_1f527); // }, new String[0], 18, 38, false,
        sEmojisMap.put("1f469_1f3fe_200d_1f527", R.drawable.emoji_1f469_1f3fe_200d_1f527); // }, new String[0], 18, 39, false,
        sEmojisMap.put("1f469_1f3ff_200d_1f527", R.drawable.emoji_1f469_1f3ff_200d_1f527); // }, new String[0], 18, 40, false
        sEmojisMap.put("1f468_200d_1f3ed", R.drawable.emoji_1f468_200d_1f3ed); // male-factory-worker
        sEmojisMap.put("1f468_1f3fb_200d_1f3ed", R.drawable.emoji_1f468_1f3fb_200d_1f3ed); // }, new String[0], 15, 18, false,
        sEmojisMap.put("1f468_1f3fc_200d_1f3ed", R.drawable.emoji_1f468_1f3fc_200d_1f3ed); // }, new String[0], 15, 19, false,
        sEmojisMap.put("1f468_1f3fd_200d_1f3ed", R.drawable.emoji_1f468_1f3fd_200d_1f3ed); // }, new String[0], 15, 20, false,
        sEmojisMap.put("1f468_1f3fe_200d_1f3ed", R.drawable.emoji_1f468_1f3fe_200d_1f3ed); // }, new String[0], 15, 21, false,
        sEmojisMap.put("1f468_1f3ff_200d_1f3ed", R.drawable.emoji_1f468_1f3ff_200d_1f3ed); // }, new String[0], 15, 22, false
        sEmojisMap.put("1f469_200d_1f3ed", R.drawable.emoji_1f469_200d_1f3ed); // female-factory-worker
        sEmojisMap.put("1f469_1f3fb_200d_1f3ed", R.drawable.emoji_1f469_1f3fb_200d_1f3ed); // }, new String[0], 18, 8, false,
        sEmojisMap.put("1f469_1f3fc_200d_1f3ed", R.drawable.emoji_1f469_1f3fc_200d_1f3ed); // }, new String[0], 18, 9, false,
        sEmojisMap.put("1f469_1f3fd_200d_1f3ed", R.drawable.emoji_1f469_1f3fd_200d_1f3ed); // }, new String[0], 18, 10, false,
        sEmojisMap.put("1f469_1f3fe_200d_1f3ed", R.drawable.emoji_1f469_1f3fe_200d_1f3ed); // }, new String[0], 18, 11, false,
        sEmojisMap.put("1f469_1f3ff_200d_1f3ed", R.drawable.emoji_1f469_1f3ff_200d_1f3ed); // }, new String[0], 18, 12, false
        sEmojisMap.put("1f468_200d_1f4bc", R.drawable.emoji_1f468_200d_1f4bc); // male-office-worker
        sEmojisMap.put("1f468_1f3fb_200d_1f4bc", R.drawable.emoji_1f468_1f3fb_200d_1f4bc); // }, new String[0], 15, 45, false,
        sEmojisMap.put("1f468_1f3fc_200d_1f4bc", R.drawable.emoji_1f468_1f3fc_200d_1f4bc); // }, new String[0], 15, 46, false,
        sEmojisMap.put("1f468_1f3fd_200d_1f4bc", R.drawable.emoji_1f468_1f3fd_200d_1f4bc); // }, new String[0], 15, 47, false,
        sEmojisMap.put("1f468_1f3fe_200d_1f4bc", R.drawable.emoji_1f468_1f3fe_200d_1f4bc); // }, new String[0], 15, 48, false,
        sEmojisMap.put("1f468_1f3ff_200d_1f4bc", R.drawable.emoji_1f468_1f3ff_200d_1f4bc); // }, new String[0], 15, 49, false
        sEmojisMap.put("1f469_200d_1f4bc", R.drawable.emoji_1f469_200d_1f4bc); // female-office-worker
        sEmojisMap.put("1f469_1f3fb_200d_1f4bc", R.drawable.emoji_1f469_1f3fb_200d_1f4bc); // }, new String[0], 18, 30, false,
        sEmojisMap.put("1f469_1f3fc_200d_1f4bc", R.drawable.emoji_1f469_1f3fc_200d_1f4bc); // }, new String[0], 18, 31, false,
        sEmojisMap.put("1f469_1f3fd_200d_1f4bc", R.drawable.emoji_1f469_1f3fd_200d_1f4bc); // }, new String[0], 18, 32, false,
        sEmojisMap.put("1f469_1f3fe_200d_1f4bc", R.drawable.emoji_1f469_1f3fe_200d_1f4bc); // }, new String[0], 18, 33, false,
        sEmojisMap.put("1f469_1f3ff_200d_1f4bc", R.drawable.emoji_1f469_1f3ff_200d_1f4bc); // }, new String[0], 18, 34, false
        sEmojisMap.put("1f468_200d_1f52c", R.drawable.emoji_1f468_200d_1f52c); // male-scientist
        sEmojisMap.put("1f468_1f3fb_200d_1f52c", R.drawable.emoji_1f468_1f3fb_200d_1f52c); // }, new String[0], 16, 0, false,
        sEmojisMap.put("1f468_1f3fc_200d_1f52c", R.drawable.emoji_1f468_1f3fc_200d_1f52c); // }, new String[0], 16, 1, false,
        sEmojisMap.put("1f468_1f3fd_200d_1f52c", R.drawable.emoji_1f468_1f3fd_200d_1f52c); // }, new String[0], 16, 2, false,
        sEmojisMap.put("1f468_1f3fe_200d_1f52c", R.drawable.emoji_1f468_1f3fe_200d_1f52c); // }, new String[0], 16, 3, false,
        sEmojisMap.put("1f468_1f3ff_200d_1f52c", R.drawable.emoji_1f468_1f3ff_200d_1f52c); // }, new String[0], 16, 4, false
        sEmojisMap.put("1f469_200d_1f52c", R.drawable.emoji_1f469_200d_1f52c); // female-scientist
        sEmojisMap.put("1f469_1f3fb_200d_1f52c", R.drawable.emoji_1f469_1f3fb_200d_1f52c); // }, new String[0], 18, 42, false,
        sEmojisMap.put("1f469_1f3fc_200d_1f52c", R.drawable.emoji_1f469_1f3fc_200d_1f52c); // }, new String[0], 18, 43, false,
        sEmojisMap.put("1f469_1f3fd_200d_1f52c", R.drawable.emoji_1f469_1f3fd_200d_1f52c); // }, new String[0], 18, 44, false,
        sEmojisMap.put("1f469_1f3fe_200d_1f52c", R.drawable.emoji_1f469_1f3fe_200d_1f52c); // }, new String[0], 18, 45, false,
        sEmojisMap.put("1f469_1f3ff_200d_1f52c", R.drawable.emoji_1f469_1f3ff_200d_1f52c); // }, new String[0], 18, 46, false
        sEmojisMap.put("1f468_200d_1f4bb", R.drawable.emoji_1f468_200d_1f4bb); // male-technologist
        sEmojisMap.put("1f468_1f3fb_200d_1f4bb", R.drawable.emoji_1f468_1f3fb_200d_1f4bb); // }, new String[0], 15, 39, false,
        sEmojisMap.put("1f468_1f3fc_200d_1f4bb", R.drawable.emoji_1f468_1f3fc_200d_1f4bb); // }, new String[0], 15, 40, false,
        sEmojisMap.put("1f468_1f3fd_200d_1f4bb", R.drawable.emoji_1f468_1f3fd_200d_1f4bb); // }, new String[0], 15, 41, false,
        sEmojisMap.put("1f468_1f3fe_200d_1f4bb", R.drawable.emoji_1f468_1f3fe_200d_1f4bb); // }, new String[0], 15, 42, false,
        sEmojisMap.put("1f468_1f3ff_200d_1f4bb", R.drawable.emoji_1f468_1f3ff_200d_1f4bb); // }, new String[0], 15, 43, false
        sEmojisMap.put("1f469_200d_1f4bb", R.drawable.emoji_1f469_200d_1f4bb); // female-technologist
        sEmojisMap.put("1f469_1f3fb_200d_1f4bb", R.drawable.emoji_1f469_1f3fb_200d_1f4bb); // }, new String[0], 18, 24, false,
        sEmojisMap.put("1f469_1f3fc_200d_1f4bb", R.drawable.emoji_1f469_1f3fc_200d_1f4bb); // }, new String[0], 18, 25, false,
        sEmojisMap.put("1f469_1f3fd_200d_1f4bb", R.drawable.emoji_1f469_1f3fd_200d_1f4bb); // }, new String[0], 18, 26, false,
        sEmojisMap.put("1f469_1f3fe_200d_1f4bb", R.drawable.emoji_1f469_1f3fe_200d_1f4bb); // }, new String[0], 18, 27, false,
        sEmojisMap.put("1f469_1f3ff_200d_1f4bb", R.drawable.emoji_1f469_1f3ff_200d_1f4bb); // }, new String[0], 18, 28, false
        sEmojisMap.put("1f468_200d_1f3a4", R.drawable.emoji_1f468_200d_1f3a4); // male-singer
        sEmojisMap.put("1f468_1f3fb_200d_1f3a4", R.drawable.emoji_1f468_1f3fb_200d_1f3a4); // }, new String[0], 15, 0, false,
        sEmojisMap.put("1f468_1f3fc_200d_1f3a4", R.drawable.emoji_1f468_1f3fc_200d_1f3a4); // }, new String[0], 15, 1, false,
        sEmojisMap.put("1f468_1f3fd_200d_1f3a4", R.drawable.emoji_1f468_1f3fd_200d_1f3a4); // }, new String[0], 15, 2, false,
        sEmojisMap.put("1f468_1f3fe_200d_1f3a4", R.drawable.emoji_1f468_1f3fe_200d_1f3a4); // }, new String[0], 15, 3, false,
        sEmojisMap.put("1f468_1f3ff_200d_1f3a4", R.drawable.emoji_1f468_1f3ff_200d_1f3a4); // }, new String[0], 15, 4, false
        sEmojisMap.put("1f469_200d_1f3a4", R.drawable.emoji_1f469_200d_1f3a4); // female-singer
        sEmojisMap.put("1f469_1f3fb_200d_1f3a4", R.drawable.emoji_1f469_1f3fb_200d_1f3a4); // }, new String[0], 17, 47, false,
        sEmojisMap.put("1f469_1f3fc_200d_1f3a4", R.drawable.emoji_1f469_1f3fc_200d_1f3a4); // }, new String[0], 17, 48, false,
        sEmojisMap.put("1f469_1f3fd_200d_1f3a4", R.drawable.emoji_1f469_1f3fd_200d_1f3a4); // }, new String[0], 17, 49, false,
        sEmojisMap.put("1f469_1f3fe_200d_1f3a4", R.drawable.emoji_1f469_1f3fe_200d_1f3a4); // }, new String[0], 17, 50, false,
        sEmojisMap.put("1f469_1f3ff_200d_1f3a4", R.drawable.emoji_1f469_1f3ff_200d_1f3a4); // }, new String[0], 17, 51, false
        sEmojisMap.put("1f468_200d_1f3a8", R.drawable.emoji_1f468_200d_1f3a8); // male-artist
        sEmojisMap.put("1f468_1f3fb_200d_1f3a8", R.drawable.emoji_1f468_1f3fb_200d_1f3a8); // }, new String[0], 15, 6, false,
        sEmojisMap.put("1f468_1f3fc_200d_1f3a8", R.drawable.emoji_1f468_1f3fc_200d_1f3a8); // }, new String[0], 15, 7, false,
        sEmojisMap.put("1f468_1f3fd_200d_1f3a8", R.drawable.emoji_1f468_1f3fd_200d_1f3a8); // }, new String[0], 15, 8, false,
        sEmojisMap.put("1f468_1f3fe_200d_1f3a8", R.drawable.emoji_1f468_1f3fe_200d_1f3a8); // }, new String[0], 15, 9, false,
        sEmojisMap.put("1f468_1f3ff_200d_1f3a8", R.drawable.emoji_1f468_1f3ff_200d_1f3a8); // }, new String[0], 15, 10, false
        sEmojisMap.put("1f469_200d_1f3a8", R.drawable.emoji_1f469_200d_1f3a8); // female-artist
        sEmojisMap.put("1f469_1f3fb_200d_1f3a8", R.drawable.emoji_1f469_1f3fb_200d_1f3a8); // }, new String[0], 17, 53, false,
        sEmojisMap.put("1f469_1f3fc_200d_1f3a8", R.drawable.emoji_1f469_1f3fc_200d_1f3a8); // }, new String[0], 17, 54, false,
        sEmojisMap.put("1f469_1f3fd_200d_1f3a8", R.drawable.emoji_1f469_1f3fd_200d_1f3a8); // }, new String[0], 17, 55, false,
        sEmojisMap.put("1f469_1f3fe_200d_1f3a8", R.drawable.emoji_1f469_1f3fe_200d_1f3a8); // }, new String[0], 17, 56, false,
        sEmojisMap.put("1f469_1f3ff_200d_1f3a8", R.drawable.emoji_1f469_1f3ff_200d_1f3a8); // }, new String[0], 18, 0, false
        sEmojisMap.put("1f468_200d_2708_fe0f", R.drawable.emoji_1f468_200d_2708_fe0f); // male-pilot
        sEmojisMap.put("1f468_1f3fb_200d_2708_fe0f", R.drawable.emoji_1f468_1f3fb_200d_2708_fe0f); // }, new String[0], 17, 15, false,
        sEmojisMap.put("1f468_1f3fc_200d_2708_fe0f", R.drawable.emoji_1f468_1f3fc_200d_2708_fe0f); // }, new String[0], 17, 16, false,
        sEmojisMap.put("1f468_1f3fd_200d_2708_fe0f", R.drawable.emoji_1f468_1f3fd_200d_2708_fe0f); // }, new String[0], 17, 17, false,
        sEmojisMap.put("1f468_1f3fe_200d_2708_fe0f", R.drawable.emoji_1f468_1f3fe_200d_2708_fe0f); // }, new String[0], 17, 18, false,
        sEmojisMap.put("1f468_1f3ff_200d_2708_fe0f", R.drawable.emoji_1f468_1f3ff_200d_2708_fe0f); // }, new String[0], 17, 19, false
        sEmojisMap.put("1f469_200d_2708_fe0f", R.drawable.emoji_1f469_200d_2708_fe0f); // female-pilot
        sEmojisMap.put("1f469_1f3fb_200d_2708_fe0f", R.drawable.emoji_1f469_1f3fb_200d_2708_fe0f); // }, new String[0], 20, 0, false,
        sEmojisMap.put("1f469_1f3fc_200d_2708_fe0f", R.drawable.emoji_1f469_1f3fc_200d_2708_fe0f); // }, new String[0], 20, 1, false,
        sEmojisMap.put("1f469_1f3fd_200d_2708_fe0f", R.drawable.emoji_1f469_1f3fd_200d_2708_fe0f); // }, new String[0], 20, 2, false,
        sEmojisMap.put("1f469_1f3fe_200d_2708_fe0f", R.drawable.emoji_1f469_1f3fe_200d_2708_fe0f); // }, new String[0], 20, 3, false,
        sEmojisMap.put("1f469_1f3ff_200d_2708_fe0f", R.drawable.emoji_1f469_1f3ff_200d_2708_fe0f); // }, new String[0], 20, 4, false
        sEmojisMap.put("1f468_200d_1f680", R.drawable.emoji_1f468_200d_1f680); // male-astronaut
        sEmojisMap.put("1f468_1f3fb_200d_1f680", R.drawable.emoji_1f468_1f3fb_200d_1f680); // }, new String[0], 16, 6, false,
        sEmojisMap.put("1f468_1f3fc_200d_1f680", R.drawable.emoji_1f468_1f3fc_200d_1f680); // }, new String[0], 16, 7, false,
        sEmojisMap.put("1f468_1f3fd_200d_1f680", R.drawable.emoji_1f468_1f3fd_200d_1f680); // }, new String[0], 16, 8, false,
        sEmojisMap.put("1f468_1f3fe_200d_1f680", R.drawable.emoji_1f468_1f3fe_200d_1f680); // }, new String[0], 16, 9, false,
        sEmojisMap.put("1f468_1f3ff_200d_1f680", R.drawable.emoji_1f468_1f3ff_200d_1f680); // }, new String[0], 16, 10, false
        sEmojisMap.put("1f469_200d_1f680", R.drawable.emoji_1f469_200d_1f680); // female-astronaut
        sEmojisMap.put("1f469_1f3fb_200d_1f680", R.drawable.emoji_1f469_1f3fb_200d_1f680); // }, new String[0], 18, 48, false,
        sEmojisMap.put("1f469_1f3fc_200d_1f680", R.drawable.emoji_1f469_1f3fc_200d_1f680); // }, new String[0], 18, 49, false,
        sEmojisMap.put("1f469_1f3fd_200d_1f680", R.drawable.emoji_1f469_1f3fd_200d_1f680); // }, new String[0], 18, 50, false,
        sEmojisMap.put("1f469_1f3fe_200d_1f680", R.drawable.emoji_1f469_1f3fe_200d_1f680); // }, new String[0], 18, 51, false,
        sEmojisMap.put("1f469_1f3ff_200d_1f680", R.drawable.emoji_1f469_1f3ff_200d_1f680); // }, new String[0], 18, 52, false
        sEmojisMap.put("1f468_200d_1f692", R.drawable.emoji_1f468_200d_1f692); // male-firefighter
        sEmojisMap.put("1f468_1f3fb_200d_1f692", R.drawable.emoji_1f468_1f3fb_200d_1f692); // }, new String[0], 16, 12, false,
        sEmojisMap.put("1f468_1f3fc_200d_1f692", R.drawable.emoji_1f468_1f3fc_200d_1f692); // }, new String[0], 16, 13, false,
        sEmojisMap.put("1f468_1f3fd_200d_1f692", R.drawable.emoji_1f468_1f3fd_200d_1f692); // }, new String[0], 16, 14, false,
        sEmojisMap.put("1f468_1f3fe_200d_1f692", R.drawable.emoji_1f468_1f3fe_200d_1f692); // }, new String[0], 16, 15, false,
        sEmojisMap.put("1f468_1f3ff_200d_1f692", R.drawable.emoji_1f468_1f3ff_200d_1f692); // }, new String[0], 16, 16, false
        sEmojisMap.put("1f469_200d_1f692", R.drawable.emoji_1f469_200d_1f692); // female-firefighter
        sEmojisMap.put("1f469_1f3fb_200d_1f692", R.drawable.emoji_1f469_1f3fb_200d_1f692); // }, new String[0], 18, 54, false,
        sEmojisMap.put("1f469_1f3fc_200d_1f692", R.drawable.emoji_1f469_1f3fc_200d_1f692); // }, new String[0], 18, 55, false,
        sEmojisMap.put("1f469_1f3fd_200d_1f692", R.drawable.emoji_1f469_1f3fd_200d_1f692); // }, new String[0], 18, 56, false,
        sEmojisMap.put("1f469_1f3fe_200d_1f692", R.drawable.emoji_1f469_1f3fe_200d_1f692); // }, new String[0], 19, 0, false,
        sEmojisMap.put("1f469_1f3ff_200d_1f692", R.drawable.emoji_1f469_1f3ff_200d_1f692); // }, new String[0], 19, 1, false
        sEmojisMap.put("1f46e", R.drawable.emoji_1f46e); // cop
        sEmojisMap.put("1f46e_1f3fb", R.drawable.emoji_1f46e_1f3fb); // }, new String[0], 21, 50, false,
        sEmojisMap.put("1f46e_1f3fc", R.drawable.emoji_1f46e_1f3fc); // }, new String[0], 21, 51, false,
        sEmojisMap.put("1f46e_1f3fd", R.drawable.emoji_1f46e_1f3fd); // }, new String[0], 21, 52, false,
        sEmojisMap.put("1f46e_1f3fe", R.drawable.emoji_1f46e_1f3fe); // }, new String[0], 21, 53, false,
        sEmojisMap.put("1f46e_1f3ff", R.drawable.emoji_1f46e_1f3ff); // }, new String[0], 21, 54, false
        sEmojisMap.put("1f46e_200d_2642_fe0f", R.drawable.emoji_1f46e_200d_2642_fe0f); // male-police-officer
        sEmojisMap.put("1f46e_1f3fb_200d_2642_fe0f", R.drawable.emoji_1f46e_1f3fb_200d_2642_fe0f); // }, new String[0], 21, 44, false,
        sEmojisMap.put("1f46e_1f3fc_200d_2642_fe0f", R.drawable.emoji_1f46e_1f3fc_200d_2642_fe0f); // }, new String[0], 21, 45, false,
        sEmojisMap.put("1f46e_1f3fd_200d_2642_fe0f", R.drawable.emoji_1f46e_1f3fd_200d_2642_fe0f); // }, new String[0], 21, 46, false,
        sEmojisMap.put("1f46e_1f3fe_200d_2642_fe0f", R.drawable.emoji_1f46e_1f3fe_200d_2642_fe0f); // }, new String[0], 21, 47, false,
        sEmojisMap.put("1f46e_1f3ff_200d_2642_fe0f", R.drawable.emoji_1f46e_1f3ff_200d_2642_fe0f); // }, new String[0], 21, 48, false
        sEmojisMap.put("1f46e_200d_2640_fe0f", R.drawable.emoji_1f46e_200d_2640_fe0f); // female-police-officer
        sEmojisMap.put("1f46e_1f3fb_200d_2640_fe0f", R.drawable.emoji_1f46e_1f3fb_200d_2640_fe0f); // }, new String[0], 21, 38, false,
        sEmojisMap.put("1f46e_1f3fc_200d_2640_fe0f", R.drawable.emoji_1f46e_1f3fc_200d_2640_fe0f); // }, new String[0], 21, 39, false,
        sEmojisMap.put("1f46e_1f3fd_200d_2640_fe0f", R.drawable.emoji_1f46e_1f3fd_200d_2640_fe0f); // }, new String[0], 21, 40, false,
        sEmojisMap.put("1f46e_1f3fe_200d_2640_fe0f", R.drawable.emoji_1f46e_1f3fe_200d_2640_fe0f); // }, new String[0], 21, 41, false,
        sEmojisMap.put("1f46e_1f3ff_200d_2640_fe0f", R.drawable.emoji_1f46e_1f3ff_200d_2640_fe0f); // }, new String[0], 21, 42, false
        sEmojisMap.put("1f575_fe0f", R.drawable.emoji_1f575_fe0f); // sleuth_or_spy
        sEmojisMap.put("1f575_1f3fb", R.drawable.emoji_1f575_1f3fb); // }, new String[0], 29, 28, false,
        sEmojisMap.put("1f575_1f3fc", R.drawable.emoji_1f575_1f3fc); // }, new String[0], 29, 29, false,
        sEmojisMap.put("1f575_1f3fd", R.drawable.emoji_1f575_1f3fd); // }, new String[0], 29, 30, false,
        sEmojisMap.put("1f575_1f3fe", R.drawable.emoji_1f575_1f3fe); // }, new String[0], 29, 31, false,
        sEmojisMap.put("1f575_1f3ff", R.drawable.emoji_1f575_1f3ff); // }, new String[0], 29, 32, false
        sEmojisMap.put("1f575_200d_2642_fe0f", R.drawable.emoji_1f575_fe0f_200d_2642_fe0f); // male-detective
        sEmojisMap.put("1f575_1f3fb_200d_2642_fe0f", R.drawable.emoji_1f575_1f3fb_200d_2642_fe0f); // }, new String[0], 29, 22, false,
        sEmojisMap.put("1f575_1f3fc_200d_2642_fe0f", R.drawable.emoji_1f575_1f3fc_200d_2642_fe0f); // }, new String[0], 29, 23, false,
        sEmojisMap.put("1f575_1f3fd_200d_2642_fe0f", R.drawable.emoji_1f575_1f3fd_200d_2642_fe0f); // }, new String[0], 29, 24, false,
        sEmojisMap.put("1f575_1f3fe_200d_2642_fe0f", R.drawable.emoji_1f575_1f3fe_200d_2642_fe0f); // }, new String[0], 29, 25, false,
        sEmojisMap.put("1f575_1f3ff_200d_2642_fe0f", R.drawable.emoji_1f575_1f3ff_200d_2642_fe0f); // }, new String[0], 29, 26, false
        sEmojisMap.put("1f575_200d_2640_fe0f", R.drawable.emoji_1f575_fe0f_200d_2640_fe0f); // female-detective
        sEmojisMap.put("1f575_1f3fb_200d_2640_fe0f", R.drawable.emoji_1f575_1f3fb_200d_2640_fe0f); // }, new String[0], 29, 16, false,
        sEmojisMap.put("1f575_1f3fc_200d_2640_fe0f", R.drawable.emoji_1f575_1f3fc_200d_2640_fe0f); // }, new String[0], 29, 17, false,
        sEmojisMap.put("1f575_1f3fd_200d_2640_fe0f", R.drawable.emoji_1f575_1f3fd_200d_2640_fe0f); // }, new String[0], 29, 18, false,
        sEmojisMap.put("1f575_1f3fe_200d_2640_fe0f", R.drawable.emoji_1f575_1f3fe_200d_2640_fe0f); // }, new String[0], 29, 19, false,
        sEmojisMap.put("1f575_1f3ff_200d_2640_fe0f", R.drawable.emoji_1f575_1f3ff_200d_2640_fe0f); // }, new String[0], 29, 20, false
        sEmojisMap.put("1f482", R.drawable.emoji_1f482); // guardsman
        sEmojisMap.put("1f482_1f3fb", R.drawable.emoji_1f482_1f3fb); // }, new String[0], 24, 21, false,
        sEmojisMap.put("1f482_1f3fc", R.drawable.emoji_1f482_1f3fc); // }, new String[0], 24, 22, false,
        sEmojisMap.put("1f482_1f3fd", R.drawable.emoji_1f482_1f3fd); // }, new String[0], 24, 23, false,
        sEmojisMap.put("1f482_1f3fe", R.drawable.emoji_1f482_1f3fe); // }, new String[0], 24, 24, false,
        sEmojisMap.put("1f482_1f3ff", R.drawable.emoji_1f482_1f3ff); // }, new String[0], 24, 25, false
         sEmojisMap.put("1f482_200d_2642_fe0f", R.drawable.emoji_1f482_200d_2642_fe0f); // male-guard
        sEmojisMap.put("1f482_1f3fb_200d_2642_fe0f", R.drawable.emoji_1f482_1f3fb_200d_2642_fe0f); // }, new String[0], 24, 15, false,
        sEmojisMap.put("1f482_1f3fc_200d_2642_fe0f", R.drawable.emoji_1f482_1f3fc_200d_2642_fe0f); // }, new String[0], 24, 16, false,
        sEmojisMap.put("1f482_1f3fd_200d_2642_fe0f", R.drawable.emoji_1f482_1f3fd_200d_2642_fe0f); // }, new String[0], 24, 17, false,
        sEmojisMap.put("1f482_1f3fe_200d_2642_fe0f", R.drawable.emoji_1f482_1f3fe_200d_2642_fe0f); // }, new String[0], 24, 18, false,
        sEmojisMap.put("1f482_1f3ff_200d_2642_fe0f", R.drawable.emoji_1f482_1f3ff_200d_2642_fe0f); // }, new String[0], 24, 19, false
        sEmojisMap.put("1f482_200d_2640_fe0f", R.drawable.emoji_1f482_200d_2640_fe0f); // female-guard
        sEmojisMap.put("1f482_1f3fb_200d_2640_fe0f", R.drawable.emoji_1f482_1f3fb_200d_2640_fe0f); // }, new String[0], 24, 9, false,
        sEmojisMap.put("1f482_1f3fc_200d_2640_fe0f", R.drawable.emoji_1f482_1f3fc_200d_2640_fe0f); // }, new String[0], 24, 10, false,
        sEmojisMap.put("1f482_1f3fd_200d_2640_fe0f", R.drawable.emoji_1f482_1f3fd_200d_2640_fe0f); // }, new String[0], 24, 11, false,
        sEmojisMap.put("1f482_1f3fe_200d_2640_fe0f", R.drawable.emoji_1f482_1f3fe_200d_2640_fe0f); // }, new String[0], 24, 12, false,
        sEmojisMap.put("1f482_1f3ff_200d_2640_fe0f", R.drawable.emoji_1f482_1f3ff_200d_2640_fe0f); // }, new String[0], 24, 13, false
        sEmojisMap.put("1f477", R.drawable.emoji_1f477); // construction_worker
        sEmojisMap.put("1f477_1f3fb", R.drawable.emoji_1f477_1f3fb); // }, new String[0], 23, 23, false,
        sEmojisMap.put("1f477_1f3fc", R.drawable.emoji_1f477_1f3fc); // }, new String[0], 23, 24, false,
        sEmojisMap.put("1f477_1f3fd", R.drawable.emoji_1f477_1f3fd); // }, new String[0], 23, 25, false,
        sEmojisMap.put("1f477_1f3fe", R.drawable.emoji_1f477_1f3fe); // }, new String[0], 23, 26, false,
        sEmojisMap.put("1f477_1f3ff", R.drawable.emoji_1f477_1f3ff); // }, new String[0], 23, 27, false
        sEmojisMap.put("1f477_200d_2642_fe0f", R.drawable.emoji_1f477_200d_2642_fe0f); // male-construction-worker
        sEmojisMap.put("1f477_1f3fb_200d_2642_fe0f", R.drawable.emoji_1f477_1f3fb_200d_2642_fe0f); // }, new String[0], 23, 17, false,
        sEmojisMap.put("1f477_1f3fc_200d_2642_fe0f", R.drawable.emoji_1f477_1f3fc_200d_2642_fe0f); // }, new String[0], 23, 18, false,
        sEmojisMap.put("1f477_1f3fd_200d_2642_fe0f", R.drawable.emoji_1f477_1f3fd_200d_2642_fe0f); // }, new String[0], 23, 19, false,
        sEmojisMap.put("1f477_1f3fe_200d_2642_fe0f", R.drawable.emoji_1f477_1f3fe_200d_2642_fe0f); // }, new String[0], 23, 20, false,
        sEmojisMap.put("1f477_1f3ff_200d_2642_fe0f", R.drawable.emoji_1f477_1f3ff_200d_2642_fe0f); // }, new String[0], 23, 21, false
        sEmojisMap.put("1f477_200d_2640_fe0f", R.drawable.emoji_1f477_200d_2640_fe0f); // female-construction-worker
        sEmojisMap.put("1f477_1f3fb_200d_2640_fe0f", R.drawable.emoji_1f477_1f3fb_200d_2640_fe0f); // }, new String[0], 23, 11, false,
        sEmojisMap.put("1f477_1f3fc_200d_2640_fe0f", R.drawable.emoji_1f477_1f3fc_200d_2640_fe0f); // }, new String[0], 23, 12, false,
        sEmojisMap.put("1f477_1f3fd_200d_2640_fe0f", R.drawable.emoji_1f477_1f3fd_200d_2640_fe0f); // }, new String[0], 23, 13, false,
        sEmojisMap.put("1f477_1f3fe_200d_2640_fe0f", R.drawable.emoji_1f477_1f3fe_200d_2640_fe0f); // }, new String[0], 23, 14, false,
        sEmojisMap.put("1f477_1f3ff_200d_2640_fe0f", R.drawable.emoji_1f477_1f3ff_200d_2640_fe0f); // }, new String[0], 23, 15, false
        sEmojisMap.put("1f934", R.drawable.emoji_1f934); // prince
        sEmojisMap.put("1f934_1f3fb", R.drawable.emoji_1f934_1f3fb); // }, new String[0], 39, 18, false,
        sEmojisMap.put("1f934_1f3fc", R.drawable.emoji_1f934_1f3fc); // }, new String[0], 39, 19, false,
        sEmojisMap.put("1f934_1f3fd", R.drawable.emoji_1f934_1f3fd); // }, new String[0], 39, 20, false,
        sEmojisMap.put("1f934_1f3fe", R.drawable.emoji_1f934_1f3fe); // }, new String[0], 39, 21, false,
        sEmojisMap.put("1f934_1f3ff", R.drawable.emoji_1f934_1f3ff); // }, new String[0], 39, 22, false
        sEmojisMap.put("1f478", R.drawable.emoji_1f478); // princess
        sEmojisMap.put("1f478_1f3fb", R.drawable.emoji_1f478_1f3fb); // }, new String[0], 23, 29, false,
        sEmojisMap.put("1f478_1f3fc", R.drawable.emoji_1f478_1f3fc); // }, new String[0], 23, 30, false,
        sEmojisMap.put("1f478_1f3fd", R.drawable.emoji_1f478_1f3fd); // }, new String[0], 23, 31, false,
        sEmojisMap.put("1f478_1f3fe", R.drawable.emoji_1f478_1f3fe); // }, new String[0], 23, 32, false,
        sEmojisMap.put("1f478_1f3ff", R.drawable.emoji_1f478_1f3ff); // }, new String[0], 23, 33, false
        sEmojisMap.put("1f473", R.drawable.emoji_1f473); // man_with_turban
        sEmojisMap.put("1f473_1f3fb", R.drawable.emoji_1f473_1f3fb); // }, new String[0], 22, 44, false,
        sEmojisMap.put("1f473_1f3fc", R.drawable.emoji_1f473_1f3fc); // }, new String[0], 22, 45, false,
        sEmojisMap.put("1f473_1f3fd", R.drawable.emoji_1f473_1f3fd); // }, new String[0], 22, 46, false,
        sEmojisMap.put("1f473_1f3fe", R.drawable.emoji_1f473_1f3fe); // }, new String[0], 22, 47, false,
        sEmojisMap.put("1f473_1f3ff", R.drawable.emoji_1f473_1f3ff); // }, new String[0], 22, 48, false
        sEmojisMap.put("1f473_200d_2642_fe0f", R.drawable.emoji_1f473_200d_2642_fe0f); // man-wearing-turban
        sEmojisMap.put("1f473_1f3fb_200d_2642_fe0f", R.drawable.emoji_1f473_1f3fb_200d_2642_fe0f); // }, new String[0], 22, 38, false,
        sEmojisMap.put("1f473_1f3fc_200d_2642_fe0f", R.drawable.emoji_1f473_1f3fc_200d_2642_fe0f); // }, new String[0], 22, 39, false,
        sEmojisMap.put("1f473_1f3fd_200d_2642_fe0f", R.drawable.emoji_1f473_1f3fd_200d_2642_fe0f); // }, new String[0], 22, 40, false,
        sEmojisMap.put("1f473_1f3fe_200d_2642_fe0f", R.drawable.emoji_1f473_1f3fe_200d_2642_fe0f); // }, new String[0], 22, 41, false,
        sEmojisMap.put("1f473_1f3ff_200d_2642_fe0f", R.drawable.emoji_1f473_1f3ff_200d_2642_fe0f); // }, new String[0], 22, 42, false
        sEmojisMap.put("1f473_200d_2640_fe0f", R.drawable.emoji_1f473_200d_2640_fe0f); // woman-wearing-turban
        sEmojisMap.put("1f473_1f3fb_200d_2640_fe0f", R.drawable.emoji_1f473_1f3fb_200d_2640_fe0f); // }, new String[0], 22, 32, false,
        sEmojisMap.put("1f473_1f3fc_200d_2640_fe0f", R.drawable.emoji_1f473_1f3fc_200d_2640_fe0f); // }, new String[0], 22, 33, false,
        sEmojisMap.put("1f473_1f3fd_200d_2640_fe0f", R.drawable.emoji_1f473_1f3fd_200d_2640_fe0f); // }, new String[0], 22, 34, false,
        sEmojisMap.put("1f473_1f3fe_200d_2640_fe0f", R.drawable.emoji_1f473_1f3fe_200d_2640_fe0f); // }, new String[0], 22, 35, false,
        sEmojisMap.put("1f473_1f3ff_200d_2640_fe0f", R.drawable.emoji_1f473_1f3ff_200d_2640_fe0f); // }, new String[0], 22, 36, false
        sEmojisMap.put("1f472", R.drawable.emoji_1f472); // man_with_gua_pi_mao
        sEmojisMap.put("1f472_1f3fb", R.drawable.emoji_1f472_1f3fb); // }, new String[0], 22, 26, false,
        sEmojisMap.put("1f472_1f3fc", R.drawable.emoji_1f472_1f3fc); // }, new String[0], 22, 27, false,
        sEmojisMap.put("1f472_1f3fd", R.drawable.emoji_1f472_1f3fd); // }, new String[0], 22, 28, false,
        sEmojisMap.put("1f472_1f3fe", R.drawable.emoji_1f472_1f3fe); // }, new String[0], 22, 29, false,
        sEmojisMap.put("1f472_1f3ff", R.drawable.emoji_1f472_1f3ff); // }, new String[0], 22, 30, false
        sEmojisMap.put("1f9d5", R.drawable.emoji_1f9d5); // person_with_headscarf
        sEmojisMap.put("1f9d5_1f3fb", R.drawable.emoji_1f9d5_1f3fb); // }, new String[0], 48, 35, false,
        sEmojisMap.put("1f9d5_1f3fc", R.drawable.emoji_1f9d5_1f3fc); // }, new String[0], 48, 36, false,
        sEmojisMap.put("1f9d5_1f3fd", R.drawable.emoji_1f9d5_1f3fd); // }, new String[0], 48, 37, false,
        sEmojisMap.put("1f9d5_1f3fe", R.drawable.emoji_1f9d5_1f3fe); // }, new String[0], 48, 38, false,
        sEmojisMap.put("1f9d5_1f3ff", R.drawable.emoji_1f9d5_1f3ff); // }, new String[0], 48, 39, false
        sEmojisMap.put("1f935", R.drawable.emoji_1f935); // man_in_tuxedo
        sEmojisMap.put("1f935_1f3fb", R.drawable.emoji_1f935_1f3fb); // }, new String[0], 39, 24, false,
        sEmojisMap.put("1f935_1f3fc", R.drawable.emoji_1f935_1f3fc); // }, new String[0], 39, 25, false,
        sEmojisMap.put("1f935_1f3fd", R.drawable.emoji_1f935_1f3fd); // }, new String[0], 39, 26, false,
        sEmojisMap.put("1f935_1f3fe", R.drawable.emoji_1f935_1f3fe); // }, new String[0], 39, 27, false,
        sEmojisMap.put("1f935_1f3ff", R.drawable.emoji_1f935_1f3ff); // }, new String[0], 39, 28, false
        sEmojisMap.put("1f470", R.drawable.emoji_1f470); // bride_with_veil
        sEmojisMap.put("1f470_1f3fb", R.drawable.emoji_1f470_1f3fb); // }, new String[0], 22, 2, false,
        sEmojisMap.put("1f470_1f3fc", R.drawable.emoji_1f470_1f3fc); // }, new String[0], 22, 3, false,
        sEmojisMap.put("1f470_1f3fd", R.drawable.emoji_1f470_1f3fd); // }, new String[0], 22, 4, false,
        sEmojisMap.put("1f470_1f3fe", R.drawable.emoji_1f470_1f3fe); // }, new String[0], 22, 5, false,
        sEmojisMap.put("1f470_1f3ff", R.drawable.emoji_1f470_1f3ff); // }, new String[0], 22, 6, false
        sEmojisMap.put("1f930", R.drawable.emoji_1f930); // pregnant_woman
        sEmojisMap.put("1f930_1f3fb", R.drawable.emoji_1f930_1f3fb); // }, new String[0], 38, 51, false,
        sEmojisMap.put("1f930_1f3fc", R.drawable.emoji_1f930_1f3fc); // }, new String[0], 38, 52, false,
        sEmojisMap.put("1f930_1f3fd", R.drawable.emoji_1f930_1f3fd); // }, new String[0], 38, 53, false,
        sEmojisMap.put("1f930_1f3fe", R.drawable.emoji_1f930_1f3fe); // }, new String[0], 38, 54, false,
        sEmojisMap.put("1f930_1f3ff", R.drawable.emoji_1f930_1f3ff); // }, new String[0], 38, 55, false
        sEmojisMap.put("1f931", R.drawable.emoji_1f931); // breast-feeding
        sEmojisMap.put("1f931_1f3fb", R.drawable.emoji_1f931_1f3fb); // }, new String[0], 39, 0, false,
        sEmojisMap.put("1f931_1f3fc", R.drawable.emoji_1f931_1f3fc); // }, new String[0], 39, 1, false,
        sEmojisMap.put("1f931_1f3fd", R.drawable.emoji_1f931_1f3fd); // }, new String[0], 39, 2, false,
        sEmojisMap.put("1f931_1f3fe", R.drawable.emoji_1f931_1f3fe); // }, new String[0], 39, 3, false,
        sEmojisMap.put("1f931_1f3ff", R.drawable.emoji_1f931_1f3ff); // }, new String[0], 39, 4, false
        sEmojisMap.put("1f47c", R.drawable.emoji_1f47c); // angel
        sEmojisMap.put("1f47c_1f3fb", R.drawable.emoji_1f47c_1f3fb); // }, new String[0], 23, 38, false,
        sEmojisMap.put("1f47c_1f3fc", R.drawable.emoji_1f47c_1f3fc); // }, new String[0], 23, 39, false,
        sEmojisMap.put("1f47c_1f3fd", R.drawable.emoji_1f47c_1f3fd); // }, new String[0], 23, 40, false,
        sEmojisMap.put("1f47c_1f3fe", R.drawable.emoji_1f47c_1f3fe); // }, new String[0], 23, 41, false,
        sEmojisMap.put("1f47c_1f3ff", R.drawable.emoji_1f47c_1f3ff); // }, new String[0], 23, 42, false
        sEmojisMap.put("1f385", R.drawable.emoji_1f385); // santa
        sEmojisMap.put("1f385_1f3fb", R.drawable.emoji_1f385_1f3fb); // }, new String[0], 7, 37, false,
        sEmojisMap.put("1f385_1f3fc", R.drawable.emoji_1f385_1f3fc); // }, new String[0], 7, 38, false,
        sEmojisMap.put("1f385_1f3fd", R.drawable.emoji_1f385_1f3fd); // }, new String[0], 7, 39, false,
        sEmojisMap.put("1f385_1f3fe", R.drawable.emoji_1f385_1f3fe); // }, new String[0], 7, 40, false,
        sEmojisMap.put("1f385_1f3ff", R.drawable.emoji_1f385_1f3ff); // }, new String[0], 7, 41, false
        sEmojisMap.put("1f936", R.drawable.emoji_1f936); // mrs_claus|mother_christmas
        sEmojisMap.put("1f936_1f3fb", R.drawable.emoji_1f936_1f3fb); // }, new String[0], 39, 30, false,
        sEmojisMap.put("1f936_1f3fc", R.drawable.emoji_1f936_1f3fc); // }, new String[0], 39, 31, false,
        sEmojisMap.put("1f936_1f3fd", R.drawable.emoji_1f936_1f3fd); // }, new String[0], 39, 32, false,
        sEmojisMap.put("1f936_1f3fe", R.drawable.emoji_1f936_1f3fe); // }, new String[0], 39, 33, false,
        sEmojisMap.put("1f936_1f3ff", R.drawable.emoji_1f936_1f3ff); // }, new String[0], 39, 34, false
        sEmojisMap.put("1f9b8", R.drawable.emoji_1f9b8); // superhero
        sEmojisMap.put("1f9b8_1f3fb", R.drawable.emoji_1f9b8_1f3fb); // }, new String[0], 43, 31, false,
        sEmojisMap.put("1f9b8_1f3fc", R.drawable.emoji_1f9b8_1f3fc); // }, new String[0], 43, 32, false,
        sEmojisMap.put("1f9b8_1f3fd", R.drawable.emoji_1f9b8_1f3fd); // }, new String[0], 43, 33, false,
        sEmojisMap.put("1f9b8_1f3fe", R.drawable.emoji_1f9b8_1f3fe); // }, new String[0], 43, 34, false,
        sEmojisMap.put("1f9b8_1f3ff", R.drawable.emoji_1f9b8_1f3ff); // }, new String[0], 43, 35, false
        sEmojisMap.put("1f9b8_200d_2642_fe0f", R.drawable.emoji_1f9b8_200d_2642_fe0f); // male_superhero
        sEmojisMap.put("1f9b8_1f3fb_200d_2642_fe0f", R.drawable.emoji_1f9b8_1f3fb_200d_2642_fe0f); // }, new String[0], 43, 25, false,
        sEmojisMap.put("1f9b8_1f3fc_200d_2642_fe0f", R.drawable.emoji_1f9b8_1f3fc_200d_2642_fe0f); // }, new String[0], 43, 26, false,
        sEmojisMap.put("1f9b8_1f3fd_200d_2642_fe0f", R.drawable.emoji_1f9b8_1f3fd_200d_2642_fe0f); // }, new String[0], 43, 27, false,
        sEmojisMap.put("1f9b8_1f3fe_200d_2642_fe0f", R.drawable.emoji_1f9b8_1f3fe_200d_2642_fe0f); // }, new String[0], 43, 28, false,
        sEmojisMap.put("1f9b8_1f3ff_200d_2642_fe0f", R.drawable.emoji_1f9b8_1f3ff_200d_2642_fe0f); // }, new String[0], 43, 29, false
        sEmojisMap.put("1f9b8_200d_2640_fe0f", R.drawable.emoji_1f9b8_200d_2640_fe0f); // female_superhero
        sEmojisMap.put("1f9b8_1f3fb_200d_2640_fe0f", R.drawable.emoji_1f9b8_1f3fb_200d_2640_fe0f); // }, new String[0], 43, 19, false,
        sEmojisMap.put("1f9b8_1f3fc_200d_2640_fe0f", R.drawable.emoji_1f9b8_1f3fc_200d_2640_fe0f); // }, new String[0], 43, 20, false,
        sEmojisMap.put("1f9b8_1f3fd_200d_2640_fe0f", R.drawable.emoji_1f9b8_1f3fd_200d_2640_fe0f); // }, new String[0], 43, 21, false,
        sEmojisMap.put("1f9b8_1f3fe_200d_2640_fe0f", R.drawable.emoji_1f9b8_1f3fe_200d_2640_fe0f); // }, new String[0], 43, 22, false,
        sEmojisMap.put("1f9b8_1f3ff_200d_2640_fe0f", R.drawable.emoji_1f9b8_1f3ff_200d_2640_fe0f); // }, new String[0], 43, 23, false
        sEmojisMap.put("1f9b9", R.drawable.emoji_1f9b9); // supervillain
        sEmojisMap.put("1f9b9_1f3fb", R.drawable.emoji_1f9b9_1f3fb); // }, new String[0], 43, 49, false,
        sEmojisMap.put("1f9b9_1f3fc", R.drawable.emoji_1f9b9_1f3fc); // }, new String[0], 43, 50, false,
        sEmojisMap.put("1f9b9_1f3fd", R.drawable.emoji_1f9b9_1f3fd); // }, new String[0], 43, 51, false,
        sEmojisMap.put("1f9b9_1f3fe", R.drawable.emoji_1f9b9_1f3fe); // }, new String[0], 43, 52, false,
        sEmojisMap.put("1f9b9_1f3ff", R.drawable.emoji_1f9b9_1f3ff); // }, new String[0], 43, 53, false
        sEmojisMap.put("1f9b9_200d_2642_fe0f", R.drawable.emoji_1f9b9_200d_2642_fe0f); // male_supervillain
        sEmojisMap.put("1f9b9_1f3fb_200d_2642_fe0f", R.drawable.emoji_1f9b9_1f3fb_200d_2642_fe0f); // }, new String[0], 43, 43, false,
        sEmojisMap.put("1f9b9_1f3fc_200d_2642_fe0f", R.drawable.emoji_1f9b9_1f3fc_200d_2642_fe0f); // }, new String[0], 43, 44, false,
        sEmojisMap.put("1f9b9_1f3fd_200d_2642_fe0f", R.drawable.emoji_1f9b9_1f3fd_200d_2642_fe0f); // }, new String[0], 43, 45, false,
        sEmojisMap.put("1f9b9_1f3fe_200d_2642_fe0f", R.drawable.emoji_1f9b9_1f3fe_200d_2642_fe0f); // }, new String[0], 43, 46, false,
        sEmojisMap.put("1f9b9_1f3ff_200d_2642_fe0f", R.drawable.emoji_1f9b9_1f3ff_200d_2642_fe0f); // }, new String[0], 43, 47, false
        sEmojisMap.put("1f9b9_200d_2640_fe0f", R.drawable.emoji_1f9b9_200d_2640_fe0f); // female_supervillain
        sEmojisMap.put("1f9b9_1f3fb_200d_2640_fe0f", R.drawable.emoji_1f9b9_1f3fb_200d_2640_fe0f); // }, new String[0], 43, 37, false,
        sEmojisMap.put("1f9b9_1f3fc_200d_2640_fe0f", R.drawable.emoji_1f9b9_1f3fc_200d_2640_fe0f); // }, new String[0], 43, 38, false,
        sEmojisMap.put("1f9b9_1f3fd_200d_2640_fe0f", R.drawable.emoji_1f9b9_1f3fd_200d_2640_fe0f); // }, new String[0], 43, 39, false,
        sEmojisMap.put("1f9b9_1f3fe_200d_2640_fe0f", R.drawable.emoji_1f9b9_1f3fe_200d_2640_fe0f); // }, new String[0], 43, 40, false,
        sEmojisMap.put("1f9b9_1f3ff_200d_2640_fe0f", R.drawable.emoji_1f9b9_1f3ff_200d_2640_fe0f); // }, new String[0], 43, 41, false
        sEmojisMap.put("1f9d9", R.drawable.emoji_1f9d9); // mage
        sEmojisMap.put("1f9d9_1f3fb", R.drawable.emoji_1f9d9_1f3fb); // }, new String[0], 49, 50, true,
        sEmojisMap.put("1f9d9_1f3fc", R.drawable.emoji_1f9d9_1f3fc); // }, new String[0], 49, 51, true,
        sEmojisMap.put("1f9d9_1f3fd", R.drawable.emoji_1f9d9_1f3fd); // }, new String[0], 49, 52, true,
        sEmojisMap.put("1f9d9_1f3fe", R.drawable.emoji_1f9d9_1f3fe); // }, new String[0], 49, 53, true,
        sEmojisMap.put("1f9d9_1f3ff", R.drawable.emoji_1f9d9_1f3ff); // }, new String[0], 49, 54, true
        sEmojisMap.put("1f9d9_200d_2642_fe0f", R.drawable.emoji_1f9d9_200d_2642_fe0f); // male_mage
        sEmojisMap.put("1f9d9_1f3fb_200d_2642_fe0f", R.drawable.emoji_1f9d9_1f3fb_200d_2642_fe0f); // }, new String[0], 49, 44, false,
        sEmojisMap.put("1f9d9_1f3fc_200d_2642_fe0f", R.drawable.emoji_1f9d9_1f3fc_200d_2642_fe0f); // }, new String[0], 49, 45, false,
        sEmojisMap.put("1f9d9_1f3fd_200d_2642_fe0f", R.drawable.emoji_1f9d9_1f3fd_200d_2642_fe0f); // }, new String[0], 49, 46, false,
        sEmojisMap.put("1f9d9_1f3fe_200d_2642_fe0f", R.drawable.emoji_1f9d9_1f3fe_200d_2642_fe0f); // }, new String[0], 49, 47, false,
        sEmojisMap.put("1f9d9_1f3ff_200d_2642_fe0f", R.drawable.emoji_1f9d9_1f3ff_200d_2642_fe0f); // }, new String[0], 49, 48, false
        sEmojisMap.put("1f9d9_200d_2640_fe0f", R.drawable.emoji_1f9d9_200d_2640_fe0f); // female_mage
        sEmojisMap.put("1f9d9_1f3fb_200d_2640_fe0f", R.drawable.emoji_1f9d9_1f3fb_200d_2640_fe0f); // }, new String[0], 49, 38, false,
        sEmojisMap.put("1f9d9_1f3fc_200d_2640_fe0f", R.drawable.emoji_1f9d9_1f3fc_200d_2640_fe0f); // }, new String[0], 49, 39, false,
        sEmojisMap.put("1f9d9_1f3fd_200d_2640_fe0f", R.drawable.emoji_1f9d9_1f3fd_200d_2640_fe0f); // }, new String[0], 49, 40, false,
        sEmojisMap.put("1f9d9_1f3fe_200d_2640_fe0f", R.drawable.emoji_1f9d9_1f3fe_200d_2640_fe0f); // }, new String[0], 49, 41, false,
        sEmojisMap.put("1f9d9_1f3ff_200d_2640_fe0f", R.drawable.emoji_1f9d9_1f3ff_200d_2640_fe0f); // }, new String[0], 49, 42, false
        sEmojisMap.put("1f9da", R.drawable.emoji_1f9da); // fairy
        sEmojisMap.put("1f9da_1f3fb", R.drawable.emoji_1f9da_1f3fb); // }, new String[0], 50, 11, true,
        sEmojisMap.put("1f9da_1f3fc", R.drawable.emoji_1f9da_1f3fc); // }, new String[0], 50, 12, true,
        sEmojisMap.put("1f9da_1f3fd", R.drawable.emoji_1f9da_1f3fd); // }, new String[0], 50, 13, true,
        sEmojisMap.put("1f9da_1f3fe", R.drawable.emoji_1f9da_1f3fe); // }, new String[0], 50, 14, true,
        sEmojisMap.put("1f9da_1f3ff", R.drawable.emoji_1f9da_1f3ff); // }, new String[0], 50, 15, true
        sEmojisMap.put("1f9da_200d_2642_fe0f", R.drawable.emoji_1f9da_200d_2642_fe0f); // male_fairy
        sEmojisMap.put("1f9da_1f3fb_200d_2642_fe0f", R.drawable.emoji_1f9da_1f3fb_200d_2642_fe0f); // }, new String[0], 50, 5, false,
        sEmojisMap.put("1f9da_1f3fc_200d_2642_fe0f", R.drawable.emoji_1f9da_1f3fc_200d_2642_fe0f); // }, new String[0], 50, 6, false,
        sEmojisMap.put("1f9da_1f3fd_200d_2642_fe0f", R.drawable.emoji_1f9da_1f3fd_200d_2642_fe0f); // }, new String[0], 50, 7, false,
        sEmojisMap.put("1f9da_1f3fe_200d_2642_fe0f", R.drawable.emoji_1f9da_1f3fe_200d_2642_fe0f); // }, new String[0], 50, 8, false,
        sEmojisMap.put("1f9da_1f3ff_200d_2642_fe0f", R.drawable.emoji_1f9da_1f3ff_200d_2642_fe0f); // }, new String[0], 50, 9, false
        sEmojisMap.put("1f9da_200d_2640_fe0f", R.drawable.emoji_1f9da_200d_2640_fe0f); // female_fairy
        sEmojisMap.put("1f9da_1f3fb_200d_2640_fe0f", R.drawable.emoji_1f9da_1f3fb_200d_2640_fe0f); // }, new String[0], 49, 56, false,
        sEmojisMap.put("1f9da_1f3fc_200d_2640_fe0f", R.drawable.emoji_1f9da_1f3fc_200d_2640_fe0f); // }, new String[0], 50, 0, false,
        sEmojisMap.put("1f9da_1f3fd_200d_2640_fe0f", R.drawable.emoji_1f9da_1f3fd_200d_2640_fe0f); // }, new String[0], 50, 1, false,
        sEmojisMap.put("1f9da_1f3fe_200d_2640_fe0f", R.drawable.emoji_1f9da_1f3fe_200d_2640_fe0f); // }, new String[0], 50, 2, false,
        sEmojisMap.put("1f9da_1f3ff_200d_2640_fe0f", R.drawable.emoji_1f9da_1f3ff_200d_2640_fe0f); // }, new String[0], 50, 3, false
        sEmojisMap.put("1f9db", R.drawable.emoji_1f9db); // vampire
        sEmojisMap.put("1f9db_1f3fb", R.drawable.emoji_1f9db_1f3fb); // }, new String[0], 50, 29, true,
        sEmojisMap.put("1f9db_1f3fc", R.drawable.emoji_1f9db_1f3fc); // }, new String[0], 50, 30, true,
        sEmojisMap.put("1f9db_1f3fd", R.drawable.emoji_1f9db_1f3fd); // }, new String[0], 50, 31, true,
        sEmojisMap.put("1f9db_1f3fe", R.drawable.emoji_1f9db_1f3fe); // }, new String[0], 50, 32, true,
        sEmojisMap.put("1f9db_1f3ff", R.drawable.emoji_1f9db_1f3ff); // }, new String[0], 50, 33, true
        sEmojisMap.put("1f9db_200d_2642_fe0f", R.drawable.emoji_1f9db_200d_2642_fe0f); // male_vampire
        sEmojisMap.put("1f9db_1f3fb_200d_2642_fe0f", R.drawable.emoji_1f9db_1f3fb_200d_2642_fe0f); // }, new String[0], 50, 23, false,
        sEmojisMap.put("1f9db_1f3fc_200d_2642_fe0f", R.drawable.emoji_1f9db_1f3fc_200d_2642_fe0f); // }, new String[0], 50, 24, false,
        sEmojisMap.put("1f9db_1f3fd_200d_2642_fe0f", R.drawable.emoji_1f9db_1f3fd_200d_2642_fe0f); // }, new String[0], 50, 25, false,
        sEmojisMap.put("1f9db_1f3fe_200d_2642_fe0f", R.drawable.emoji_1f9db_1f3fe_200d_2642_fe0f); // }, new String[0], 50, 26, false,
        sEmojisMap.put("1f9db_1f3ff_200d_2642_fe0f", R.drawable.emoji_1f9db_1f3ff_200d_2642_fe0f); // }, new String[0], 50, 27, false
        sEmojisMap.put("1f9db_200d_2640_fe0f", R.drawable.emoji_1f9db_200d_2640_fe0f); // female_vampire
        sEmojisMap.put("1f9db_1f3fb_200d_2640_fe0f", R.drawable.emoji_1f9db_1f3fb_200d_2640_fe0f); // }, new String[0], 50, 17, false,
        sEmojisMap.put("1f9db_1f3fc_200d_2640_fe0f", R.drawable.emoji_1f9db_1f3fc_200d_2640_fe0f); // }, new String[0], 50, 18, false,
        sEmojisMap.put("1f9db_1f3fd_200d_2640_fe0f", R.drawable.emoji_1f9db_1f3fd_200d_2640_fe0f); // }, new String[0], 50, 19, false,
        sEmojisMap.put("1f9db_1f3fe_200d_2640_fe0f", R.drawable.emoji_1f9db_1f3fe_200d_2640_fe0f); // }, new String[0], 50, 20, false,
        sEmojisMap.put("1f9db_1f3ff_200d_2640_fe0f", R.drawable.emoji_1f9db_1f3ff_200d_2640_fe0f); // }, new String[0], 50, 21, false
        sEmojisMap.put("1f9dc", R.drawable.emoji_1f9dc); // merperson
        sEmojisMap.put("1f9dc_1f3fb", R.drawable.emoji_1f9dc_1f3fb); // }, new String[0], 50, 47, true,
        sEmojisMap.put("1f9dc_1f3fc", R.drawable.emoji_1f9dc_1f3fc); // }, new String[0], 50, 48, true,
        sEmojisMap.put("1f9dc_1f3fd", R.drawable.emoji_1f9dc_1f3fd); // }, new String[0], 50, 49, true,
        sEmojisMap.put("1f9dc_1f3fe", R.drawable.emoji_1f9dc_1f3fe); // }, new String[0], 50, 50, true,
        sEmojisMap.put("1f9dc_1f3ff", R.drawable.emoji_1f9dc_1f3ff); // }, new String[0], 50, 51, true
        sEmojisMap.put("1f9dc_200d_2642_fe0f", R.drawable.emoji_1f9dc_200d_2642_fe0f); // merman
        sEmojisMap.put("1f9dc_1f3fb_200d_2642_fe0f", R.drawable.emoji_1f9dc_1f3fb_200d_2642_fe0f); // }, new String[0], 50, 41, false,
        sEmojisMap.put("1f9dc_1f3fc_200d_2642_fe0f", R.drawable.emoji_1f9dc_1f3fc_200d_2642_fe0f); // }, new String[0], 50, 42, false,
        sEmojisMap.put("1f9dc_1f3fd_200d_2642_fe0f", R.drawable.emoji_1f9dc_1f3fd_200d_2642_fe0f); // }, new String[0], 50, 43, false,
        sEmojisMap.put("1f9dc_1f3fe_200d_2642_fe0f", R.drawable.emoji_1f9dc_1f3fe_200d_2642_fe0f); // }, new String[0], 50, 44, false,
        sEmojisMap.put("1f9dc_1f3ff_200d_2642_fe0f", R.drawable.emoji_1f9dc_1f3ff_200d_2642_fe0f); // }, new String[0], 50, 45, false
        sEmojisMap.put("1f9dc_200d_2640_fe0f", R.drawable.emoji_1f9dc_200d_2640_fe0f); // mermaid
        sEmojisMap.put("1f9dc_1f3fb_200d_2640_fe0f", R.drawable.emoji_1f9dc_1f3fb_200d_2640_fe0f); // }, new String[0], 50, 35, false,
        sEmojisMap.put("1f9dc_1f3fc_200d_2640_fe0f", R.drawable.emoji_1f9dc_1f3fc_200d_2640_fe0f); // }, new String[0], 50, 36, false,
        sEmojisMap.put("1f9dc_1f3fd_200d_2640_fe0f", R.drawable.emoji_1f9dc_1f3fd_200d_2640_fe0f); // }, new String[0], 50, 37, false,
        sEmojisMap.put("1f9dc_1f3fe_200d_2640_fe0f", R.drawable.emoji_1f9dc_1f3fe_200d_2640_fe0f); // }, new String[0], 50, 38, false,
        sEmojisMap.put("1f9dc_1f3ff_200d_2640_fe0f", R.drawable.emoji_1f9dc_1f3ff_200d_2640_fe0f); // }, new String[0], 50, 39, false
        sEmojisMap.put("1f9dd", R.drawable.emoji_1f9dd); // elf
        sEmojisMap.put("1f9dd_1f3fb", R.drawable.emoji_1f9dd_1f3fb); // }, new String[0], 51, 8, true,
        sEmojisMap.put("1f9dd_1f3fc", R.drawable.emoji_1f9dd_1f3fc); // }, new String[0], 51, 9, true,
        sEmojisMap.put("1f9dd_1f3fd", R.drawable.emoji_1f9dd_1f3fd); // }, new String[0], 51, 10, true,
        sEmojisMap.put("1f9dd_1f3fe", R.drawable.emoji_1f9dd_1f3fe); // }, new String[0], 51, 11, true,
        sEmojisMap.put("1f9dd_1f3ff", R.drawable.emoji_1f9dd_1f3ff); // }, new String[0], 51, 12, true
        sEmojisMap.put("1f9dd_200d_2642_fe0f", R.drawable.emoji_1f9dd_200d_2642_fe0f); // male_elf
        sEmojisMap.put("1f9dd_1f3fb_200d_2642_fe0f", R.drawable.emoji_1f9dd_1f3fb_200d_2642_fe0f); // }, new String[0], 51, 2, false,
        sEmojisMap.put("1f9dd_1f3fc_200d_2642_fe0f", R.drawable.emoji_1f9dd_1f3fc_200d_2642_fe0f); // }, new String[0], 51, 3, false,
        sEmojisMap.put("1f9dd_1f3fd_200d_2642_fe0f", R.drawable.emoji_1f9dd_1f3fd_200d_2642_fe0f); // }, new String[0], 51, 4, false,
        sEmojisMap.put("1f9dd_1f3fe_200d_2642_fe0f", R.drawable.emoji_1f9dd_1f3fe_200d_2642_fe0f); // }, new String[0], 51, 5, false,
        sEmojisMap.put("1f9dd_1f3ff_200d_2642_fe0f", R.drawable.emoji_1f9dd_1f3ff_200d_2642_fe0f); // }, new String[0], 51, 6, false
        sEmojisMap.put("1f9dd_200d_2640_fe0f", R.drawable.emoji_1f9dd_200d_2640_fe0f); // female_elf
        sEmojisMap.put("1f9dd_1f3fb_200d_2640_fe0f", R.drawable.emoji_1f9dd_1f3fb_200d_2640_fe0f); // }, new String[0], 50, 53, false,
        sEmojisMap.put("1f9dd_1f3fc_200d_2640_fe0f", R.drawable.emoji_1f9dd_1f3fc_200d_2640_fe0f); // }, new String[0], 50, 54, false,
        sEmojisMap.put("1f9dd_1f3fd_200d_2640_fe0f", R.drawable.emoji_1f9dd_1f3fd_200d_2640_fe0f); // }, new String[0], 50, 55, false,
        sEmojisMap.put("1f9dd_1f3fe_200d_2640_fe0f", R.drawable.emoji_1f9dd_1f3fe_200d_2640_fe0f); // }, new String[0], 50, 56, false,
        sEmojisMap.put("1f9dd_1f3ff_200d_2640_fe0f", R.drawable.emoji_1f9dd_1f3ff_200d_2640_fe0f); // }, new String[0], 51, 0, false
        sEmojisMap.put("1f9de", R.drawable.emoji_1f9de); // genie
        sEmojisMap.put("1f9de_200d_2642_fe0f", R.drawable.emoji_1f9de_200d_2642_fe0f); // male_genie
        sEmojisMap.put("1f9de_200d_2640_fe0f", R.drawable.emoji_1f9de_200d_2640_fe0f); // female_genie
        sEmojisMap.put("1f9df", R.drawable.emoji_1f9df); // zombie
        sEmojisMap.put("1f9df_200d_2642_fe0f", R.drawable.emoji_1f9df_200d_2642_fe0f); // male_zombie
        sEmojisMap.put("1f9df_200d_2640_fe0f", R.drawable.emoji_1f9df_200d_2640_fe0f); // female_zombie
        sEmojisMap.put("1f486", R.drawable.emoji_1f486); // massage
        sEmojisMap.put("1f486_1f3fb", R.drawable.emoji_1f486_1f3fb); // }, new String[0], 24, 52, false,
        sEmojisMap.put("1f486_1f3fc", R.drawable.emoji_1f486_1f3fc); // }, new String[0], 24, 53, false,
        sEmojisMap.put("1f486_1f3fd", R.drawable.emoji_1f486_1f3fd); // }, new String[0], 24, 54, false,
        sEmojisMap.put("1f486_1f3fe", R.drawable.emoji_1f486_1f3fe); // }, new String[0], 24, 55, false,
        sEmojisMap.put("1f486_1f3ff", R.drawable.emoji_1f486_1f3ff); // }, new String[0], 24, 56, false
        sEmojisMap.put("1f486_200d_2642_fe0f", R.drawable.emoji_1f486_200d_2642_fe0f); // man-getting-massage
        sEmojisMap.put("1f486_1f3fb_200d_2642_fe0f", R.drawable.emoji_1f486_1f3fb_200d_2642_fe0f); // }, new String[0], 24, 46, false,
        sEmojisMap.put("1f486_1f3fc_200d_2642_fe0f", R.drawable.emoji_1f486_1f3fc_200d_2642_fe0f); // }, new String[0], 24, 47, false,
        sEmojisMap.put("1f486_1f3fd_200d_2642_fe0f", R.drawable.emoji_1f486_1f3fd_200d_2642_fe0f); // }, new String[0], 24, 48, false,
        sEmojisMap.put("1f486_1f3fe_200d_2642_fe0f", R.drawable.emoji_1f486_1f3fe_200d_2642_fe0f); // }, new String[0], 24, 49, false,
        sEmojisMap.put("1f486_1f3ff_200d_2642_fe0f", R.drawable.emoji_1f486_1f3ff_200d_2642_fe0f); // }, new String[0], 24, 50, false
        sEmojisMap.put("1f486_200d_2640_fe0f", R.drawable.emoji_1f486_200d_2640_fe0f); // woman-getting-massage
        sEmojisMap.put("1f486_1f3fb_200d_2640_fe0f", R.drawable.emoji_1f486_1f3fb_200d_2640_fe0f); // }, new String[0], 24, 40, false,
        sEmojisMap.put("1f486_1f3fc_200d_2640_fe0f", R.drawable.emoji_1f486_1f3fc_200d_2640_fe0f); // }, new String[0], 24, 41, false,
        sEmojisMap.put("1f486_1f3fd_200d_2640_fe0f", R.drawable.emoji_1f486_1f3fd_200d_2640_fe0f); // }, new String[0], 24, 42, false,
        sEmojisMap.put("1f486_1f3fe_200d_2640_fe0f", R.drawable.emoji_1f486_1f3fe_200d_2640_fe0f); // }, new String[0], 24, 43, false,
        sEmojisMap.put("1f486_1f3ff_200d_2640_fe0f", R.drawable.emoji_1f486_1f3ff_200d_2640_fe0f); // }, new String[0], 24, 44, false
        sEmojisMap.put("1f487", R.drawable.emoji_1f487); // haircut
        sEmojisMap.put("1f487_1f3fb", R.drawable.emoji_1f487_1f3fb); // }, new String[0], 25, 13, false,
        sEmojisMap.put("1f487_1f3fc", R.drawable.emoji_1f487_1f3fc); // }, new String[0], 25, 14, false,
        sEmojisMap.put("1f487_1f3fd", R.drawable.emoji_1f487_1f3fd); // }, new String[0], 25, 15, false,
        sEmojisMap.put("1f487_1f3fe", R.drawable.emoji_1f487_1f3fe); // }, new String[0], 25, 16, false,
        sEmojisMap.put("1f487_1f3ff", R.drawable.emoji_1f487_1f3ff); // }, new String[0], 25, 17, false
        sEmojisMap.put("1f487_200d_2642_fe0f", R.drawable.emoji_1f487_200d_2642_fe0f);       // man-getting-haircut
        sEmojisMap.put("1f487_1f3fb_200d_2642_fe0f", R.drawable.emoji_1f487_1f3fb_200d_2642_fe0f); // }, new String[0], 25, 7, false,
        sEmojisMap.put("1f487_1f3fc_200d_2642_fe0f", R.drawable.emoji_1f487_1f3fc_200d_2642_fe0f); // }, new String[0], 25, 8, false,
        sEmojisMap.put("1f487_1f3fd_200d_2642_fe0f", R.drawable.emoji_1f487_1f3fd_200d_2642_fe0f); // }, new String[0], 25, 9, false,
        sEmojisMap.put("1f487_1f3fe_200d_2642_fe0f", R.drawable.emoji_1f487_1f3fe_200d_2642_fe0f); // }, new String[0], 25, 10, false,
        sEmojisMap.put("1f487_1f3ff_200d_2642_fe0f", R.drawable.emoji_1f487_1f3ff_200d_2642_fe0f); // }, new String[0], 25, 11, false
        sEmojisMap.put("1f487_200d_2640_fe0f", R.drawable.emoji_1f487_200d_2640_fe0f); // woman-getting-haircut
        sEmojisMap.put("1f487_1f3fb_200d_2640_fe0f", R.drawable.emoji_1f487_1f3fb_200d_2640_fe0f); // }, new String[0], 25, 1, false,
        sEmojisMap.put("1f487_1f3fc_200d_2640_fe0f", R.drawable.emoji_1f487_1f3fc_200d_2640_fe0f); // }, new String[0], 25, 2, false,
        sEmojisMap.put("1f487_1f3fd_200d_2640_fe0f", R.drawable.emoji_1f487_1f3fd_200d_2640_fe0f); // }, new String[0], 25, 3, false,
        sEmojisMap.put("1f487_1f3fe_200d_2640_fe0f", R.drawable.emoji_1f487_1f3fe_200d_2640_fe0f); // }, new String[0], 25, 4, false,
        sEmojisMap.put("1f487_1f3ff_200d_2640_fe0f", R.drawable.emoji_1f487_1f3ff_200d_2640_fe0f); // }, new String[0], 25, 5, false
        sEmojisMap.put("1f6b6", R.drawable.emoji_1f6b6); // walking
        sEmojisMap.put("1f6b6_1f3fb", R.drawable.emoji_1f6b6_1f3fb); // }, new String[0], 36, 3, false,
        sEmojisMap.put("1f6b6_1f3fc", R.drawable.emoji_1f6b6_1f3fc); // }, new String[0], 36, 4, false,
        sEmojisMap.put("1f6b6_1f3fd", R.drawable.emoji_1f6b6_1f3fd); // }, new String[0], 36, 5, false,
        sEmojisMap.put("1f6b6_1f3fe", R.drawable.emoji_1f6b6_1f3fe); // }, new String[0], 36, 6, false,
        sEmojisMap.put("1f6b6_1f3ff", R.drawable.emoji_1f6b6_1f3ff); // }, new String[0], 36, 7, false
        sEmojisMap.put("1f6b6_200d_2642_fe0f", R.drawable.emoji_1f6b6_200d_2642_fe0f); // man-walking
        sEmojisMap.put("1f6b6_1f3fb_200d_2642_fe0f", R.drawable.emoji_1f6b6_1f3fb_200d_2642_fe0f); // }, new String[0], 35, 54, false,
        sEmojisMap.put("1f6b6_1f3fc_200d_2642_fe0f", R.drawable.emoji_1f6b6_1f3fc_200d_2642_fe0f); // }, new String[0], 35, 55, false,
        sEmojisMap.put("1f6b6_1f3fd_200d_2642_fe0f", R.drawable.emoji_1f6b6_1f3fd_200d_2642_fe0f); // }, new String[0], 35, 56, false,
        sEmojisMap.put("1f6b6_1f3fe_200d_2642_fe0f", R.drawable.emoji_1f6b6_1f3fe_200d_2642_fe0f); // }, new String[0], 36, 0, false,
        sEmojisMap.put("1f6b6_1f3ff_200d_2642_fe0f", R.drawable.emoji_1f6b6_1f3ff_200d_2642_fe0f); // }, new String[0], 36, 1, false
        sEmojisMap.put("1f6b6_200d_2640_fe0f", R.drawable.emoji_1f6b6_200d_2640_fe0f); // woman-walking
        sEmojisMap.put("1f6b6_1f3fb_200d_2640_fe0f", R.drawable.emoji_1f6b6_1f3fb_200d_2640_fe0f); // }, new String[0], 35, 48, false,
        sEmojisMap.put("1f6b6_1f3fc_200d_2640_fe0f", R.drawable.emoji_1f6b6_1f3fc_200d_2640_fe0f); // }, new String[0], 35, 49, false,
        sEmojisMap.put("1f6b6_1f3fd_200d_2640_fe0f", R.drawable.emoji_1f6b6_1f3fd_200d_2640_fe0f); // }, new String[0], 35, 50, false,
        sEmojisMap.put("1f6b6_1f3fe_200d_2640_fe0f", R.drawable.emoji_1f6b6_1f3fe_200d_2640_fe0f); // }, new String[0], 35, 51, false,
        sEmojisMap.put("1f6b6_1f3ff_200d_2640_fe0f", R.drawable.emoji_1f6b6_1f3ff_200d_2640_fe0f); // }, new String[0], 35, 52, false
        sEmojisMap.put("1f9cd", R.drawable.emoji_1f9cd); // standing_person
        sEmojisMap.put("1f9cd_1f3fb", R.drawable.emoji_1f9cd_1f3fb); // }, new String[0], 44, 32, false,
        sEmojisMap.put("1f9cd_1f3fc", R.drawable.emoji_1f9cd_1f3fc); // }, new String[0], 44, 33, false,
        sEmojisMap.put("1f9cd_1f3fd", R.drawable.emoji_1f9cd_1f3fd); // }, new String[0], 44, 34, false,
        sEmojisMap.put("1f9cd_1f3fe", R.drawable.emoji_1f9cd_1f3fe); // }, new String[0], 44, 35, false,
        sEmojisMap.put("1f9cd_1f3ff", R.drawable.emoji_1f9cd_1f3ff); // }, new String[0], 44, 36, false
        sEmojisMap.put("1f9cd_200d_2642_fe0f", R.drawable.emoji_1f9cd_200d_2642_fe0f); // man_standing
        sEmojisMap.put("1f9cd_1f3fb_200d_2642_fe0f", R.drawable.emoji_1f9cd_1f3fb_200d_2642_fe0f); // }, new String[0], 44, 26, false,
        sEmojisMap.put("1f9cd_1f3fc_200d_2642_fe0f", R.drawable.emoji_1f9cd_1f3fc_200d_2642_fe0f); // }, new String[0], 44, 27, false,
        sEmojisMap.put("1f9cd_1f3fd_200d_2642_fe0f", R.drawable.emoji_1f9cd_1f3fd_200d_2642_fe0f); // }, new String[0], 44, 28, false,
        sEmojisMap.put("1f9cd_1f3fe_200d_2642_fe0f", R.drawable.emoji_1f9cd_1f3fe_200d_2642_fe0f); // }, new String[0], 44, 29, false,
        sEmojisMap.put("1f9cd_1f3ff_200d_2642_fe0f", R.drawable.emoji_1f9cd_1f3ff_200d_2642_fe0f); // }, new String[0], 44, 30, false
        sEmojisMap.put("1f9cd_200d_2640_fe0f", R.drawable.emoji_1f9cd_200d_2640_fe0f); // woman_standing
        sEmojisMap.put("1f9cd_1f3fb_200d_2640_fe0f", R.drawable.emoji_1f9cd_1f3fb_200d_2640_fe0f); // }, new String[0], 44, 20, false,
        sEmojisMap.put("1f9cd_1f3fc_200d_2640_fe0f", R.drawable.emoji_1f9cd_1f3fc_200d_2640_fe0f); // }, new String[0], 44, 21, false,
        sEmojisMap.put("1f9cd_1f3fd_200d_2640_fe0f", R.drawable.emoji_1f9cd_1f3fd_200d_2640_fe0f); // }, new String[0], 44, 22, false,
        sEmojisMap.put("1f9cd_1f3fe_200d_2640_fe0f", R.drawable.emoji_1f9cd_1f3fe_200d_2640_fe0f); // }, new String[0], 44, 23, false,
        sEmojisMap.put("1f9cd_1f3ff_200d_2640_fe0f", R.drawable.emoji_1f9cd_1f3ff_200d_2640_fe0f); // }, new String[0], 44, 24, false
        sEmojisMap.put("1f9ce", R.drawable.emoji_1f9ce); // kneeling_person
        sEmojisMap.put("1f9ce_1f3fb", R.drawable.emoji_1f9ce_1f3fb); // }, new String[0], 44, 50, false,
        sEmojisMap.put("1f9ce_1f3fc", R.drawable.emoji_1f9ce_1f3fc); // }, new String[0], 44, 51, false,
        sEmojisMap.put("1f9ce_1f3fd", R.drawable.emoji_1f9ce_1f3fd); // }, new String[0], 44, 52, false,
        sEmojisMap.put("1f9ce_1f3fe", R.drawable.emoji_1f9ce_1f3fe); // }, new String[0], 44, 53, false,
        sEmojisMap.put("1f9ce_1f3ff", R.drawable.emoji_1f9ce_1f3ff); // }, new String[0], 44, 54, false
        sEmojisMap.put("1f9ce_200d_2642_fe0f", R.drawable.emoji_1f9ce_200d_2642_fe0f); // man_kneeling
        sEmojisMap.put("1f9ce_1f3fb_200d_2642_fe0f", R.drawable.emoji_1f9ce_1f3fb_200d_2642_fe0f); // }, new String[0], 44, 44, false,
        sEmojisMap.put("1f9ce_1f3fc_200d_2642_fe0f", R.drawable.emoji_1f9ce_1f3fc_200d_2642_fe0f); // }, new String[0], 44, 45, false,
        sEmojisMap.put("1f9ce_1f3fd_200d_2642_fe0f", R.drawable.emoji_1f9ce_1f3fd_200d_2642_fe0f); // }, new String[0], 44, 46, false,
        sEmojisMap.put("1f9ce_1f3fe_200d_2642_fe0f", R.drawable.emoji_1f9ce_1f3fe_200d_2642_fe0f); // }, new String[0], 44, 47, false,
        sEmojisMap.put("1f9ce_1f3ff_200d_2642_fe0f", R.drawable.emoji_1f9ce_1f3ff_200d_2642_fe0f); // }, new String[0], 44, 48, false
        sEmojisMap.put("1f9ce_200d_2640_fe0f", R.drawable.emoji_1f9ce_200d_2640_fe0f); // woman_kneeling
        sEmojisMap.put("1f9ce_1f3fb_200d_2640_fe0f", R.drawable.emoji_1f9ce_1f3fb_200d_2640_fe0f); // }, new String[0], 44, 38, false,
        sEmojisMap.put("1f9ce_1f3fc_200d_2640_fe0f", R.drawable.emoji_1f9ce_1f3fc_200d_2640_fe0f); // }, new String[0], 44, 39, false,
        sEmojisMap.put("1f9ce_1f3fd_200d_2640_fe0f", R.drawable.emoji_1f9ce_1f3fd_200d_2640_fe0f); // }, new String[0], 44, 40, false,
        sEmojisMap.put("1f9ce_1f3fe_200d_2640_fe0f", R.drawable.emoji_1f9ce_1f3fe_200d_2640_fe0f); // }, new String[0], 44, 41, false,
        sEmojisMap.put("1f9ce_1f3ff_200d_2640_fe0f", R.drawable.emoji_1f9ce_1f3ff_200d_2640_fe0f); // }, new String[0], 44, 42, false
        sEmojisMap.put("1f468_200d_1f9af", R.drawable.emoji_1f468_200d_1f9af); // man_with_probing_cane
        sEmojisMap.put("1f468_1f3fb_200d_1f9af", R.drawable.emoji_1f468_1f3fb_200d_1f9af); // }, new String[0], 16, 18, false,
        sEmojisMap.put("1f468_1f3fc_200d_1f9af", R.drawable.emoji_1f468_1f3fc_200d_1f9af); // }, new String[0], 16, 19, false,
        sEmojisMap.put("1f468_1f3fd_200d_1f9af", R.drawable.emoji_1f468_1f3fd_200d_1f9af); // }, new String[0], 16, 20, false,
        sEmojisMap.put("1f468_1f3fe_200d_1f9af", R.drawable.emoji_1f468_1f3fe_200d_1f9af); // }, new String[0], 16, 21, false,
        sEmojisMap.put("1f468_1f3ff_200d_1f9af", R.drawable.emoji_1f468_1f3ff_200d_1f9af); // }, new String[0], 16, 22, false
        sEmojisMap.put("1f469_200d_1f9af", R.drawable.emoji_1f469_200d_1f9af); // woman_with_probing_cane
        sEmojisMap.put("1f469_1f3fb_200d_1f9af", R.drawable.emoji_1f469_1f3fb_200d_1f9af); // }, new String[0], 19, 3, false,
        sEmojisMap.put("1f469_1f3fc_200d_1f9af", R.drawable.emoji_1f469_1f3fc_200d_1f9af); // }, new String[0], 19, 4, false,
        sEmojisMap.put("1f469_1f3fd_200d_1f9af", R.drawable.emoji_1f469_1f3fd_200d_1f9af); // }, new String[0], 19, 5, false,
        sEmojisMap.put("1f469_1f3fe_200d_1f9af", R.drawable.emoji_1f469_1f3fe_200d_1f9af); // }, new String[0], 19, 6, false,
        sEmojisMap.put("1f469_1f3ff_200d_1f9af", R.drawable.emoji_1f469_1f3ff_200d_1f9af); // }, new String[0], 19, 7, false
        sEmojisMap.put("1f468_200d_1f9bc", R.drawable.emoji_1f468_200d_1f9bc); // man_in_motorized_wheelchair
        sEmojisMap.put("1f468_1f3fb_200d_1f9bc", R.drawable.emoji_1f468_1f3fb_200d_1f9bc); // }, new String[0], 16, 48, false,
        sEmojisMap.put("1f468_1f3fc_200d_1f9bc", R.drawable.emoji_1f468_1f3fc_200d_1f9bc); // }, new String[0], 16, 49, false,
        sEmojisMap.put("1f468_1f3fd_200d_1f9bc", R.drawable.emoji_1f468_1f3fd_200d_1f9bc); // }, new String[0], 16, 50, false,
        sEmojisMap.put("1f468_1f3fe_200d_1f9bc", R.drawable.emoji_1f468_1f3fe_200d_1f9bc); // }, new String[0], 16, 51, false,
        sEmojisMap.put("1f468_1f3ff_200d_1f9bc", R.drawable.emoji_1f468_1f3ff_200d_1f9bc); // }, new String[0], 16, 52, false
        sEmojisMap.put("1f469_200d_1f9bc", R.drawable.emoji_1f469_200d_1f9bc); // woman_in_motorized_wheelchair
        sEmojisMap.put("1f469_1f3fb_200d_1f9bc", R.drawable.emoji_1f469_1f3fb_200d_1f9bc); // }, new String[0], 19, 33, false,
        sEmojisMap.put("1f469_1f3fc_200d_1f9bc", R.drawable.emoji_1f469_1f3fc_200d_1f9bc); // }, new String[0], 19, 34, false,
        sEmojisMap.put("1f469_1f3fd_200d_1f9bc", R.drawable.emoji_1f469_1f3fd_200d_1f9bc); // }, new String[0], 19, 35, false,
        sEmojisMap.put("1f469_1f3fe_200d_1f9bc", R.drawable.emoji_1f469_1f3fe_200d_1f9bc); // }, new String[0], 19, 36, false,
        sEmojisMap.put("1f469_1f3ff_200d_1f9bc", R.drawable.emoji_1f469_1f3ff_200d_1f9bc); // }, new String[0], 19, 37, false
        sEmojisMap.put("1f468_200d_1f9bd", R.drawable.emoji_1f468_200d_1f9bd); // man_in_manual_wheelchair
        sEmojisMap.put("1f468_1f3fb_200d_1f9bd", R.drawable.emoji_1f468_1f3fb_200d_1f9bd); // }, new String[0], 16, 54, false,
        sEmojisMap.put("1f468_1f3fc_200d_1f9bd", R.drawable.emoji_1f468_1f3fc_200d_1f9bd); // }, new String[0], 16, 55, false,
        sEmojisMap.put("1f468_1f3fd_200d_1f9bd", R.drawable.emoji_1f468_1f3fd_200d_1f9bd); // }, new String[0], 16, 56, false,
        sEmojisMap.put("1f468_1f3fe_200d_1f9bd", R.drawable.emoji_1f468_1f3fe_200d_1f9bd); // }, new String[0], 17, 0, false,
        sEmojisMap.put("1f468_1f3ff_200d_1f9bd", R.drawable.emoji_1f468_1f3ff_200d_1f9bd); // }, new String[0], 17, 1, false
        sEmojisMap.put("1f469_200d_1f9bd", R.drawable.emoji_1f469_200d_1f9bd); // woman_in_manual_wheelchair
        sEmojisMap.put("1f469_1f3fb_200d_1f9bd", R.drawable.emoji_1f469_1f3fb_200d_1f9bd); // }, new String[0], 19, 39, false,
        sEmojisMap.put("1f469_1f3fc_200d_1f9bd", R.drawable.emoji_1f469_1f3fc_200d_1f9bd); // }, new String[0], 19, 40, false,
        sEmojisMap.put("1f469_1f3fd_200d_1f9bd", R.drawable.emoji_1f469_1f3fd_200d_1f9bd); // }, new String[0], 19, 41, false,
        sEmojisMap.put("1f469_1f3fe_200d_1f9bd", R.drawable.emoji_1f469_1f3fe_200d_1f9bd); // }, new String[0], 19, 42, false,
        sEmojisMap.put("1f469_1f3ff_200d_1f9bd", R.drawable.emoji_1f469_1f3ff_200d_1f9bd); // }, new String[0], 19, 43, false
        sEmojisMap.put("1f3c3", R.drawable.emoji_1f3c3); // runner|running
        sEmojisMap.put("1f3c3_1f3fb", R.drawable.emoji_1f3c3_1f3fb); // }, new String[0], 9, 2, false,
        sEmojisMap.put("1f3c3_1f3fc", R.drawable.emoji_1f3c3_1f3fc); // }, new String[0], 9, 3, false,
        sEmojisMap.put("1f3c3_1f3fd", R.drawable.emoji_1f3c3_1f3fd); // }, new String[0], 9, 4, false,
        sEmojisMap.put("1f3c3_1f3fe", R.drawable.emoji_1f3c3_1f3fe); // }, new String[0], 9, 5, false,
        sEmojisMap.put("1f3c3_1f3ff", R.drawable.emoji_1f3c3_1f3ff); // }, new String[0], 9, 6, false
        sEmojisMap.put("1f3c3_200d_2642_fe0f", R.drawable.emoji_1f3c3_200d_2642_fe0f); // man-running
        sEmojisMap.put("1f3c3_1f3fb_200d_2642_fe0f", R.drawable.emoji_1f3c3_1f3fb_200d_2642_fe0f); // }, new String[0], 8, 53, false,
        sEmojisMap.put("1f3c3_1f3fc_200d_2642_fe0f", R.drawable.emoji_1f3c3_1f3fc_200d_2642_fe0f); // }, new String[0], 8, 54, false,
        sEmojisMap.put("1f3c3_1f3fd_200d_2642_fe0f", R.drawable.emoji_1f3c3_1f3fd_200d_2642_fe0f); // }, new String[0], 8, 55, false,
        sEmojisMap.put("1f3c3_1f3fe_200d_2642_fe0f", R.drawable.emoji_1f3c3_1f3fe_200d_2642_fe0f); // }, new String[0], 8, 56, false,
        sEmojisMap.put("1f3c3_1f3ff_200d_2642_fe0f", R.drawable.emoji_1f3c3_1f3ff_200d_2642_fe0f); // }, new String[0], 9, 0, false
        sEmojisMap.put("1f3c3_200d_2640_fe0f", R.drawable.emoji_1f3c3_200d_2640_fe0f); // woman-running
        sEmojisMap.put("1f3c3_1f3fb_200d_2640_fe0f", R.drawable.emoji_1f3c3_1f3fb_200d_2640_fe0f); // }, new String[0], 8, 47, false,
        sEmojisMap.put("1f3c3_1f3fc_200d_2640_fe0f", R.drawable.emoji_1f3c3_1f3fc_200d_2640_fe0f); // }, new String[0], 8, 48, false,
        sEmojisMap.put("1f3c3_1f3fd_200d_2640_fe0f", R.drawable.emoji_1f3c3_1f3fd_200d_2640_fe0f); // }, new String[0], 8, 49, false,
        sEmojisMap.put("1f3c3_1f3fe_200d_2640_fe0f", R.drawable.emoji_1f3c3_1f3fe_200d_2640_fe0f); // }, new String[0], 8, 50, false,
        sEmojisMap.put("1f3c3_1f3ff_200d_2640_fe0f", R.drawable.emoji_1f3c3_1f3ff_200d_2640_fe0f); // }, new String[0], 8, 51, false
        sEmojisMap.put("1f483", R.drawable.emoji_1f483); // dancer
        sEmojisMap.put("1f483_1f3fb", R.drawable.emoji_1f483_1f3fb); // }, new String[0], 24, 27, false,
        sEmojisMap.put("1f483_1f3fc", R.drawable.emoji_1f483_1f3fc); // }, new String[0], 24, 28, false,
        sEmojisMap.put("1f483_1f3fd", R.drawable.emoji_1f483_1f3fd); // }, new String[0], 24, 29, false,
        sEmojisMap.put("1f483_1f3fe", R.drawable.emoji_1f483_1f3fe); // }, new String[0], 24, 30, false,
        sEmojisMap.put("1f483_1f3ff", R.drawable.emoji_1f483_1f3ff); // }, new String[0], 24, 31, false
        sEmojisMap.put("1f57a", R.drawable.emoji_1f57a); // man_dancing
        sEmojisMap.put("1f57a_1f3fb", R.drawable.emoji_1f57a_1f3fb); // }, new String[0], 29, 38, false,
        sEmojisMap.put("1f57a_1f3fc", R.drawable.emoji_1f57a_1f3fc); // }, new String[0], 29, 39, false,
        sEmojisMap.put("1f57a_1f3fd", R.drawable.emoji_1f57a_1f3fd); // }, new String[0], 29, 40, false,
        sEmojisMap.put("1f57a_1f3fe", R.drawable.emoji_1f57a_1f3fe); // }, new String[0], 29, 41, false,
        sEmojisMap.put("1f57a_1f3ff", R.drawable.emoji_1f57a_1f3ff); // }, new String[0], 29, 42, false
        sEmojisMap.put("1f574_fe0f", R.drawable.emoji_1f574_fe0f); // man_in_business_suit_levitating
        sEmojisMap.put("1f574_1f3fb", R.drawable.emoji_1f574_1f3fb); // }, new String[0], 29, 10, false,
        sEmojisMap.put("1f574_1f3fc", R.drawable.emoji_1f574_1f3fc); // }, new String[0], 29, 11, false,
        sEmojisMap.put("1f574_1f3fd", R.drawable.emoji_1f574_1f3fd); // }, new String[0], 29, 12, false,
        sEmojisMap.put("1f574_1f3fe", R.drawable.emoji_1f574_1f3fe); // }, new String[0], 29, 13, false,
        sEmojisMap.put("1f574_1f3ff", R.drawable.emoji_1f574_1f3ff); // }, new String[0], 29, 14, false
        sEmojisMap.put("1f46f", R.drawable.emoji_1f46f); // dancers
        sEmojisMap.put("1f46f_200d_2642_fe0f", R.drawable.emoji_1f46f_200d_2642_fe0f); // man-with-bunny-ears-partying
        sEmojisMap.put("1f46f_200d_2640_fe0f", R.drawable.emoji_1f46f_200d_2640_fe0f); // woman-with-bunny-ears-partying
        sEmojisMap.put("1f9d6", R.drawable.emoji_1f9d6); // person_in_steamy_room
        sEmojisMap.put("1f9d6_1f3fb", R.drawable.emoji_1f9d6_1f3fb); // }, new String[0], 48, 53, true,
        sEmojisMap.put("1f9d6_1f3fc", R.drawable.emoji_1f9d6_1f3fc); // }, new String[0], 48, 54, true,
        sEmojisMap.put("1f9d6_1f3fd", R.drawable.emoji_1f9d6_1f3fd); // }, new String[0], 48, 55, true,
        sEmojisMap.put("1f9d6_1f3fe", R.drawable.emoji_1f9d6_1f3fe); // }, new String[0], 48, 56, true,
        sEmojisMap.put("1f9d6_1f3ff", R.drawable.emoji_1f9d6_1f3ff); // }, new String[0], 49, 0, true
        sEmojisMap.put("1f9d6_200d_2642_fe0f", R.drawable.emoji_1f9d6_200d_2642_fe0f); // man_in_steamy_room
        sEmojisMap.put("1f9d6_1f3fb_200d_2642_fe0f", R.drawable.emoji_1f9d6_1f3fb_200d_2642_fe0f); // }, new String[0], 48, 47, false,
        sEmojisMap.put("1f9d6_1f3fc_200d_2642_fe0f", R.drawable.emoji_1f9d6_1f3fc_200d_2642_fe0f); // }, new String[0], 48, 48, false,
        sEmojisMap.put("1f9d6_1f3fd_200d_2642_fe0f", R.drawable.emoji_1f9d6_1f3fd_200d_2642_fe0f); // }, new String[0], 48, 49, false,
        sEmojisMap.put("1f9d6_1f3fe_200d_2642_fe0f", R.drawable.emoji_1f9d6_1f3fe_200d_2642_fe0f); // }, new String[0], 48, 50, false,
        sEmojisMap.put("1f9d6_1f3ff_200d_2642_fe0f", R.drawable.emoji_1f9d6_1f3ff_200d_2642_fe0f); // }, new String[0], 48, 51, false
        sEmojisMap.put("1f9d6_200d_2640_fe0f", R.drawable.emoji_1f9d6_200d_2640_fe0f); // woman_in_steamy_room
        sEmojisMap.put("1f9d6_1f3fb_200d_2640_fe0f", R.drawable.emoji_1f9d6_1f3fb_200d_2640_fe0f); // }, new String[0], 48, 41, false,
        sEmojisMap.put("1f9d6_1f3fc_200d_2640_fe0f", R.drawable.emoji_1f9d6_1f3fc_200d_2640_fe0f); // }, new String[0], 48, 42, false,
        sEmojisMap.put("1f9d6_1f3fd_200d_2640_fe0f", R.drawable.emoji_1f9d6_1f3fd_200d_2640_fe0f); // }, new String[0], 48, 43, false,
        sEmojisMap.put("1f9d6_1f3fe_200d_2640_fe0f", R.drawable.emoji_1f9d6_1f3fe_200d_2640_fe0f); // }, new String[0], 48, 44, false,
        sEmojisMap.put("1f9d6_1f3ff_200d_2640_fe0f", R.drawable.emoji_1f9d6_1f3ff_200d_2640_fe0f); // }, new String[0], 48, 45, false
        sEmojisMap.put("1f9d7", R.drawable.emoji_1f9d7); // person_climbing
        sEmojisMap.put("1f9d7_1f3fb", R.drawable.emoji_1f9d7_1f3fb); // }, new String[0], 49, 14, true,
        sEmojisMap.put("1f9d7_1f3fc", R.drawable.emoji_1f9d7_1f3fc); // }, new String[0], 49, 15, true,
        sEmojisMap.put("1f9d7_1f3fd", R.drawable.emoji_1f9d7_1f3fd); // }, new String[0], 49, 16, true,
        sEmojisMap.put("1f9d7_1f3fe", R.drawable.emoji_1f9d7_1f3fe); // }, new String[0], 49, 17, true,
        sEmojisMap.put("1f9d7_1f3ff", R.drawable.emoji_1f9d7_1f3ff); // }, new String[0], 49, 18, true
        sEmojisMap.put("1f9d7_200d_2642_fe0f", R.drawable.emoji_1f9d7_200d_2642_fe0f); // man_climbing
        sEmojisMap.put("1f9d7_1f3fb_200d_2642_fe0f", R.drawable.emoji_1f9d7_1f3fb_200d_2642_fe0f); // }, new String[0], 49, 8, false,
        sEmojisMap.put("1f9d7_1f3fc_200d_2642_fe0f", R.drawable.emoji_1f9d7_1f3fc_200d_2642_fe0f); // }, new String[0], 49, 9, false,
        sEmojisMap.put("1f9d7_1f3fd_200d_2642_fe0f", R.drawable.emoji_1f9d7_1f3fd_200d_2642_fe0f); // }, new String[0], 49, 10, false,
        sEmojisMap.put("1f9d7_1f3fe_200d_2642_fe0f", R.drawable.emoji_1f9d7_1f3fe_200d_2642_fe0f); // }, new String[0], 49, 11, false,
        sEmojisMap.put("1f9d7_1f3ff_200d_2642_fe0f", R.drawable.emoji_1f9d7_1f3ff_200d_2642_fe0f); // }, new String[0], 49, 12, false
        sEmojisMap.put("1f9d7_200d_2640_fe0f", R.drawable.emoji_1f9d7_200d_2640_fe0f); // woman_climbing
        sEmojisMap.put("1f9d7_1f3fb_200d_2640_fe0f", R.drawable.emoji_1f9d7_1f3fb_200d_2640_fe0f); // }, new String[0], 49, 2, false,
        sEmojisMap.put("1f9d7_1f3fc_200d_2640_fe0f", R.drawable.emoji_1f9d7_1f3fc_200d_2640_fe0f); // }, new String[0], 49, 3, false,
        sEmojisMap.put("1f9d7_1f3fd_200d_2640_fe0f", R.drawable.emoji_1f9d7_1f3fd_200d_2640_fe0f); // }, new String[0], 49, 4, false,
        sEmojisMap.put("1f9d7_1f3fe_200d_2640_fe0f", R.drawable.emoji_1f9d7_1f3fe_200d_2640_fe0f); // }, new String[0], 49, 5, false,
        sEmojisMap.put("1f9d7_1f3ff_200d_2640_fe0f", R.drawable.emoji_1f9d7_1f3ff_200d_2640_fe0f); // }, new String[0], 49, 6, false
        sEmojisMap.put("1f93a", R.drawable.emoji_1f93a); // fencer
        sEmojisMap.put("1f3c7", R.drawable.emoji_1f3c7); // horse_racing
        sEmojisMap.put("1f3c7_1f3fb", R.drawable.emoji_1f3c7_1f3fb); // }, new String[0], 9, 28, false,
        sEmojisMap.put("1f3c7_1f3fc", R.drawable.emoji_1f3c7_1f3fc); // }, new String[0], 9, 29, false,
        sEmojisMap.put("1f3c7_1f3fd", R.drawable.emoji_1f3c7_1f3fd); // }, new String[0], 9, 30, false,
        sEmojisMap.put("1f3c7_1f3fe", R.drawable.emoji_1f3c7_1f3fe); // }, new String[0], 9, 31, false,
        sEmojisMap.put("1f3c7_1f3ff", R.drawable.emoji_1f3c7_1f3ff); // }, new String[0], 9, 32, false
        sEmojisMap.put("26f7", R.drawable.emoji_26f7); // skier
        // sEmojisMap.put("26f7_fe0f", R.drawable.emoji_26f7_fe0f); // skier
        sEmojisMap.put("1f3c2", R.drawable.emoji_1f3c2); // snowboarder
        sEmojisMap.put("1f3c2_1f3fb", R.drawable.emoji_1f3c2_1f3fb); // }, new String[0], 8, 41, false,
        sEmojisMap.put("1f3c2_1f3fc", R.drawable.emoji_1f3c2_1f3fc); // }, new String[0], 8, 42, false,
        sEmojisMap.put("1f3c2_1f3fd", R.drawable.emoji_1f3c2_1f3fd); // }, new String[0], 8, 43, false,
        sEmojisMap.put("1f3c2_1f3fe", R.drawable.emoji_1f3c2_1f3fe); // }, new String[0], 8, 44, false,
        sEmojisMap.put("1f3c2_1f3ff", R.drawable.emoji_1f3c2_1f3ff); // }, new String[0], 8, 45, false
        sEmojisMap.put("1f3cc_fe0f", R.drawable.emoji_1f3cc_fe0f); // golfer
        sEmojisMap.put("1f3cc_1f3fb", R.drawable.emoji_1f3cc_1f3fb); // }, new String[0], 10, 27, false,
        sEmojisMap.put("1f3cc_1f3fc", R.drawable.emoji_1f3cc_1f3fc); // }, new String[0], 10, 28, false,
        sEmojisMap.put("1f3cc_1f3fd", R.drawable.emoji_1f3cc_1f3fd); // }, new String[0], 10, 29, false,
        sEmojisMap.put("1f3cc_1f3fe", R.drawable.emoji_1f3cc_1f3fe); // }, new String[0], 10, 30, false,
        sEmojisMap.put("1f3cc_1f3ff", R.drawable.emoji_1f3cc_1f3ff); // }, new String[0], 10, 31, false
        sEmojisMap.put("1f3cc_200d_2642_fe0f", R.drawable.emoji_1f3cc_fe0f_200d_2642_fe0f); // man-golfing
        sEmojisMap.put("1f3cc_1f3fb_200d_2642_fe0f", R.drawable.emoji_1f3cc_1f3fb_200d_2642_fe0f); // }, new String[0], 10, 21, false,
        sEmojisMap.put("1f3cc_1f3fc_200d_2642_fe0f", R.drawable.emoji_1f3cc_1f3fc_200d_2642_fe0f); // }, new String[0], 10, 22, false,
        sEmojisMap.put("1f3cc_1f3fd_200d_2642_fe0f", R.drawable.emoji_1f3cc_1f3fd_200d_2642_fe0f); // }, new String[0], 10, 23, false,
        sEmojisMap.put("1f3cc_1f3fe_200d_2642_fe0f", R.drawable.emoji_1f3cc_1f3fe_200d_2642_fe0f); // }, new String[0], 10, 24, false,
        sEmojisMap.put("1f3cc_1f3ff_200d_2642_fe0f", R.drawable.emoji_1f3cc_1f3ff_200d_2642_fe0f); // }, new String[0], 10, 25, false
        sEmojisMap.put("1f3cc_200d_2640_fe0f", R.drawable.emoji_1f3cc_fe0f_200d_2640_fe0f); // woman-golfing
        sEmojisMap.put("1f3cc_1f3fb_200d_2640_fe0f", R.drawable.emoji_1f3cc_1f3fb_200d_2640_fe0f); // }, new String[0], 10, 15, false,
        sEmojisMap.put("1f3cc_1f3fc_200d_2640_fe0f", R.drawable.emoji_1f3cc_1f3fc_200d_2640_fe0f); // }, new String[0], 10, 16, false,
        sEmojisMap.put("1f3cc_1f3fd_200d_2640_fe0f", R.drawable.emoji_1f3cc_1f3fd_200d_2640_fe0f); // }, new String[0], 10, 17, false,
        sEmojisMap.put("1f3cc_1f3fe_200d_2640_fe0f", R.drawable.emoji_1f3cc_1f3fe_200d_2640_fe0f); // }, new String[0], 10, 18, false,
        sEmojisMap.put("1f3cc_1f3ff_200d_2640_fe0f", R.drawable.emoji_1f3cc_1f3ff_200d_2640_fe0f); // }, new String[0], 10, 19, false
        sEmojisMap.put("1f3c4", R.drawable.emoji_1f3c4); // surfer
        sEmojisMap.put("1f3c4_1f3fb", R.drawable.emoji_1f3c4_1f3fb); // }, new String[0], 9, 20, false,
        sEmojisMap.put("1f3c4_1f3fc", R.drawable.emoji_1f3c4_1f3fc); // }, new String[0], 9, 21, false,
        sEmojisMap.put("1f3c4_1f3fd", R.drawable.emoji_1f3c4_1f3fd); // }, new String[0], 9, 22, false,
        sEmojisMap.put("1f3c4_1f3fe", R.drawable.emoji_1f3c4_1f3fe); // }, new String[0], 9, 23, false,
        sEmojisMap.put("1f3c4_1f3ff", R.drawable.emoji_1f3c4_1f3ff); // }, new String[0], 9, 24, false
        sEmojisMap.put("1f3c4_200d_2642_fe0f", R.drawable.emoji_1f3c4_200d_2642_fe0f); // man-surfing
        sEmojisMap.put("1f3c4_1f3fb_200d_2642_fe0f", R.drawable.emoji_1f3c4_1f3fb_200d_2642_fe0f); // }, new String[0], 9, 14, false,
        sEmojisMap.put("1f3c4_1f3fc_200d_2642_fe0f", R.drawable.emoji_1f3c4_1f3fc_200d_2642_fe0f); // }, new String[0], 9, 15, false,
        sEmojisMap.put("1f3c4_1f3fd_200d_2642_fe0f", R.drawable.emoji_1f3c4_1f3fd_200d_2642_fe0f); // }, new String[0], 9, 16, false,
        sEmojisMap.put("1f3c4_1f3fe_200d_2642_fe0f", R.drawable.emoji_1f3c4_1f3fe_200d_2642_fe0f); // }, new String[0], 9, 17, false,
        sEmojisMap.put("1f3c4_1f3ff_200d_2642_fe0f", R.drawable.emoji_1f3c4_1f3ff_200d_2642_fe0f); // }, new String[0], 9, 18, false
        sEmojisMap.put("00d_2640_fe0f", R.drawable.emoji_1f3c4_200d_2640_fe0f); // woman-surfing
        sEmojisMap.put("1f3c4_1f3fb_200d_2640_fe0f", R.drawable.emoji_1f3c4_1f3fb_200d_2640_fe0f); // }, new String[0], 9, 8, false,
        sEmojisMap.put("1f3c4_1f3fc_200d_2640_fe0f", R.drawable.emoji_1f3c4_1f3fc_200d_2640_fe0f); // }, new String[0], 9, 9, false,
        sEmojisMap.put("1f3c4_1f3fd_200d_2640_fe0f", R.drawable.emoji_1f3c4_1f3fd_200d_2640_fe0f); // }, new String[0], 9, 10, false,
        sEmojisMap.put("1f3c4_1f3fe_200d_2640_fe0f", R.drawable.emoji_1f3c4_1f3fe_200d_2640_fe0f); // }, new String[0], 9, 11, false,
        sEmojisMap.put("1f3c4_1f3ff_200d_2640_fe0f", R.drawable.emoji_1f3c4_1f3ff_200d_2640_fe0f); // }, new String[0], 9, 12, false
        sEmojisMap.put("1f6a3", R.drawable.emoji_1f6a3); // rowboat
        sEmojisMap.put("1f6a3_1f3fb", R.drawable.emoji_1f6a3_1f3fb); // }, new String[0], 34, 47, false,
        sEmojisMap.put("1f6a3_1f3fc", R.drawable.emoji_1f6a3_1f3fc); // }, new String[0], 34, 48, false,
        sEmojisMap.put("1f6a3_1f3fd", R.drawable.emoji_1f6a3_1f3fd); // }, new String[0], 34, 49, false,
        sEmojisMap.put("1f6a3_1f3fe", R.drawable.emoji_1f6a3_1f3fe); // }, new String[0], 34, 50, false,
        sEmojisMap.put("1f6a3_1f3ff", R.drawable.emoji_1f6a3_1f3ff); // }, new String[0], 34, 51, false
        sEmojisMap.put("1f6a3_200d_2642_fe0f", R.drawable.emoji_1f6a3_200d_2642_fe0f); // man-rowing-boat
        sEmojisMap.put("1f6a3_1f3fb_200d_2642_fe0f", R.drawable.emoji_1f6a3_1f3fb_200d_2642_fe0f); // }, new String[0], 34, 41, false,
        sEmojisMap.put("1f6a3_1f3fc_200d_2642_fe0f", R.drawable.emoji_1f6a3_1f3fc_200d_2642_fe0f); // }, new String[0], 34, 42, false,
        sEmojisMap.put("1f6a3_1f3fd_200d_2642_fe0f", R.drawable.emoji_1f6a3_1f3fd_200d_2642_fe0f); // }, new String[0], 34, 43, false,
        sEmojisMap.put("1f6a3_1f3fe_200d_2642_fe0f", R.drawable.emoji_1f6a3_1f3fe_200d_2642_fe0f); // }, new String[0], 34, 44, false,
        sEmojisMap.put("1f6a3_1f3ff_200d_2642_fe0f", R.drawable.emoji_1f6a3_1f3ff_200d_2642_fe0f); // }, new String[0], 34, 45, false
        sEmojisMap.put("1f6a3_200d_2640_fe0f", R.drawable.emoji_1f6a3_200d_2640_fe0f); // woman-rowing-boat
        sEmojisMap.put("1f6a3_1f3fb_200d_2640_fe0f", R.drawable.emoji_1f6a3_1f3fb_200d_2640_fe0f); // }, new String[0], 34, 35, false,
        sEmojisMap.put("1f6a3_1f3fc_200d_2640_fe0f", R.drawable.emoji_1f6a3_1f3fc_200d_2640_fe0f); // }, new String[0], 34, 36, false,
        sEmojisMap.put("1f6a3_1f3fd_200d_2640_fe0f", R.drawable.emoji_1f6a3_1f3fd_200d_2640_fe0f); // }, new String[0], 34, 37, false,
        sEmojisMap.put("1f6a3_1f3fe_200d_2640_fe0f", R.drawable.emoji_1f6a3_1f3fe_200d_2640_fe0f); // }, new String[0], 34, 38, false,
        sEmojisMap.put("1f6a3_1f3ff_200d_2640_fe0f", R.drawable.emoji_1f6a3_1f3ff_200d_2640_fe0f); // }, new String[0], 34, 39, false
        sEmojisMap.put("1f3ca", R.drawable.emoji_1f3ca); // swimmer
        sEmojisMap.put("1f3ca_1f3fb", R.drawable.emoji_1f3ca_1f3fb); // }, new String[0], 9, 48, false,
        sEmojisMap.put("1f3ca_1f3fc", R.drawable.emoji_1f3ca_1f3fc); // }, new String[0], 9, 49, false,
        sEmojisMap.put("1f3ca_1f3fd", R.drawable.emoji_1f3ca_1f3fd); // }, new String[0], 9, 50, false,
        sEmojisMap.put("1f3ca_1f3fe", R.drawable.emoji_1f3ca_1f3fe); // }, new String[0], 9, 51, false,
        sEmojisMap.put("1f3ca_1f3ff", R.drawable.emoji_1f3ca_1f3ff); // }, new String[0], 9, 52, false
        sEmojisMap.put("1f3ca_200d_2642_fe0f", R.drawable.emoji_1f3ca_200d_2642_fe0f); // man-swimming
        sEmojisMap.put("1f3ca_1f3fb_200d_2642_fe0f", R.drawable.emoji_1f3ca_1f3fb_200d_2642_fe0f); // }, new String[0], 9, 42, false,
        sEmojisMap.put("1f3ca_1f3fc_200d_2642_fe0f", R.drawable.emoji_1f3ca_1f3fc_200d_2642_fe0f); // }, new String[0], 9, 43, false,
        sEmojisMap.put("1f3ca_1f3fd_200d_2642_fe0f", R.drawable.emoji_1f3ca_1f3fd_200d_2642_fe0f); // }, new String[0], 9, 44, false,
        sEmojisMap.put("1f3ca_1f3fe_200d_2642_fe0f", R.drawable.emoji_1f3ca_1f3fe_200d_2642_fe0f); // }, new String[0], 9, 45, false,
        sEmojisMap.put("1f3ca_1f3ff_200d_2642_fe0f", R.drawable.emoji_1f3ca_1f3ff_200d_2642_fe0f); // }, new String[0], 9, 46, false
        sEmojisMap.put("1f3ca_200d_2640_fe0f", R.drawable.emoji_1f3ca_200d_2640_fe0f); // woman-swimming
        sEmojisMap.put("1f3ca_1f3fb_200d_2640_fe0f", R.drawable.emoji_1f3ca_1f3fb_200d_2640_fe0f); // }, new String[0], 9, 36, false,
        sEmojisMap.put("1f3ca_1f3fc_200d_2640_fe0f", R.drawable.emoji_1f3ca_1f3fc_200d_2640_fe0f); // }, new String[0], 9, 37, false,
        sEmojisMap.put("1f3ca_1f3fd_200d_2640_fe0f", R.drawable.emoji_1f3ca_1f3fd_200d_2640_fe0f); // }, new String[0], 9, 38, false,
        sEmojisMap.put("1f3ca_1f3fe_200d_2640_fe0f", R.drawable.emoji_1f3ca_1f3fe_200d_2640_fe0f); // }, new String[0], 9, 39, false,
        sEmojisMap.put("1f3ca_1f3ff_200d_2640_fe0f", R.drawable.emoji_1f3ca_1f3ff_200d_2640_fe0f); // }, new String[0], 9, 40, false
        sEmojisMap.put("26f9_fe0f", R.drawable.emoji_26f9_fe0f); // person_with_ball
        sEmojisMap.put("26f9_1f3fb", R.drawable.emoji_26f9_1f3fb); // }, new String[0], 54, 32, false,
        sEmojisMap.put("26f9_1f3fc", R.drawable.emoji_26f9_1f3fc); // }, new String[0], 54, 33, false,
        sEmojisMap.put("26f9_1f3fd", R.drawable.emoji_26f9_1f3fd); // }, new String[0], 54, 34, false,
        sEmojisMap.put("26f9_1f3fe", R.drawable.emoji_26f9_1f3fe); // }, new String[0], 54, 35, false,
        sEmojisMap.put("26f9_1f3ff", R.drawable.emoji_26f9_1f3ff); // }, new String[0], 54, 36, false
        sEmojisMap.put("26f9_200d_2642_fe0f", R.drawable.emoji_26f9_fe0f_200d_2642_fe0f); // man-bouncing-ball
        sEmojisMap.put("26f9_1f3fb_200d_2642_fe0f", R.drawable.emoji_26f9_1f3fb_200d_2642_fe0f); // }, new String[0], 54, 26, false,
        sEmojisMap.put("26f9_1f3fc_200d_2642_fe0f", R.drawable.emoji_26f9_1f3fc_200d_2642_fe0f); // }, new String[0], 54, 27, false,
        sEmojisMap.put("26f9_1f3fd_200d_2642_fe0f", R.drawable.emoji_26f9_1f3fd_200d_2642_fe0f); // }, new String[0], 54, 28, false,
        sEmojisMap.put("26f9_1f3fe_200d_2642_fe0f", R.drawable.emoji_26f9_1f3fe_200d_2642_fe0f); // }, new String[0], 54, 29, false,
        sEmojisMap.put("26f9_1f3ff_200d_2642_fe0f", R.drawable.emoji_26f9_1f3ff_200d_2642_fe0f); // }, new String[0], 54, 30, false
        sEmojisMap.put("26f9_200d_2640_fe0f", R.drawable.emoji_26f9_fe0f_200d_2640_fe0f); // woman-bouncing-ball
        sEmojisMap.put("26f9_1f3fb_200d_2640_fe0f", R.drawable.emoji_26f9_1f3fb_200d_2640_fe0f); // }, new String[0], 54, 20, false,
        sEmojisMap.put("26f9_1f3fc_200d_2640_fe0f", R.drawable.emoji_26f9_1f3fc_200d_2640_fe0f); // }, new String[0], 54, 21, false,
        sEmojisMap.put("26f9_1f3fd_200d_2640_fe0f", R.drawable.emoji_26f9_1f3fd_200d_2640_fe0f); // }, new String[0], 54, 22, false,
        sEmojisMap.put("26f9_1f3fe_200d_2640_fe0f", R.drawable.emoji_26f9_1f3fe_200d_2640_fe0f); // }, new String[0], 54, 23, false,
        sEmojisMap.put("26f9_1f3ff_200d_2640_fe0f", R.drawable.emoji_26f9_1f3ff_200d_2640_fe0f); // }, new String[0], 54, 24, false
        sEmojisMap.put("1f3cb_fe0f", R.drawable.emoji_1f3cb_fe0f); // weight_lifter
        sEmojisMap.put("1f3cb_1f3fb", R.drawable.emoji_1f3cb_1f3fb); // }, new String[0], 10, 9, false,
        sEmojisMap.put("1f3cb_1f3fc", R.drawable.emoji_1f3cb_1f3fc); // }, new String[0], 10, 10, false,
        sEmojisMap.put("1f3cb_1f3fd", R.drawable.emoji_1f3cb_1f3fd); // }, new String[0], 10, 11, false,
        sEmojisMap.put("1f3cb_1f3fe", R.drawable.emoji_1f3cb_1f3fe); // }, new String[0], 10, 12, false,
        sEmojisMap.put("1f3cb_1f3ff", R.drawable.emoji_1f3cb_1f3ff); // }, new String[0], 10, 13, false
        sEmojisMap.put("1f3cb_200d_2642_fe0f", R.drawable.emoji_1f3cb_fe0f_200d_2642_fe0f); // man-lifting-weights
        sEmojisMap.put("1f3cb_1f3fb_200d_2642_fe0f", R.drawable.emoji_1f3cb_1f3fb_200d_2642_fe0f); // }, new String[0], 10, 3, false,
        sEmojisMap.put("1f3cb_1f3fc_200d_2642_fe0f", R.drawable.emoji_1f3cb_1f3fc_200d_2642_fe0f); // }, new String[0], 10, 4, false,
        sEmojisMap.put("1f3cb_1f3fd_200d_2642_fe0f", R.drawable.emoji_1f3cb_1f3fd_200d_2642_fe0f); // }, new String[0], 10, 5, false,
        sEmojisMap.put("1f3cb_1f3fe_200d_2642_fe0f", R.drawable.emoji_1f3cb_1f3fe_200d_2642_fe0f); // }, new String[0], 10, 6, false,
        sEmojisMap.put("1f3cb_1f3ff_200d_2642_fe0f", R.drawable.emoji_1f3cb_1f3ff_200d_2642_fe0f); // }, new String[0], 10, 7, false
        sEmojisMap.put("1f3cb_200d_2640_fe0f", R.drawable.emoji_1f3cb_fe0f_200d_2640_fe0f); // woman-lifting-weights
        sEmojisMap.put("1f3cb_1f3fb_200d_2640_fe0f", R.drawable.emoji_1f3cb_1f3fb_200d_2640_fe0f); // }, new String[0], 9, 54, false,
        sEmojisMap.put("1f3cb_1f3fc_200d_2640_fe0f", R.drawable.emoji_1f3cb_1f3fc_200d_2640_fe0f); // }, new String[0], 9, 55, false,
        sEmojisMap.put("1f3cb_1f3fd_200d_2640_fe0f", R.drawable.emoji_1f3cb_1f3fd_200d_2640_fe0f); // }, new String[0], 9, 56, false,
        sEmojisMap.put("1f3cb_1f3fe_200d_2640_fe0f", R.drawable.emoji_1f3cb_1f3fe_200d_2640_fe0f); // }, new String[0], 10, 0, false,
        sEmojisMap.put("1f3cb_1f3ff_200d_2640_fe0f", R.drawable.emoji_1f3cb_1f3ff_200d_2640_fe0f); // }, new String[0], 10, 1, false
        sEmojisMap.put("1f6b4", R.drawable.emoji_1f6b4); // bicyclist
        sEmojisMap.put("1f6b4_1f3fb", R.drawable.emoji_1f6b4_1f3fb); // }, new String[0], 35, 24, false,
        sEmojisMap.put("1f6b4_1f3fc", R.drawable.emoji_1f6b4_1f3fc); // }, new String[0], 35, 25, false,
        sEmojisMap.put("1f6b4_1f3fd", R.drawable.emoji_1f6b4_1f3fd); // }, new String[0], 35, 26, false,
        sEmojisMap.put("1f6b4_1f3fe", R.drawable.emoji_1f6b4_1f3fe); // }, new String[0], 35, 27, false,
        sEmojisMap.put("1f6b4_1f3ff", R.drawable.emoji_1f6b4_1f3ff); // }, new String[0], 35, 28, false
        sEmojisMap.put("1f6b4_200d_2642_fe0f", R.drawable.emoji_1f6b4_200d_2642_fe0f); // man-biking
        sEmojisMap.put("1f6b4_1f3fb_200d_2642_fe0f", R.drawable.emoji_1f6b4_1f3fb_200d_2642_fe0f); // }, new String[0], 35, 18, false,
        sEmojisMap.put("1f6b4_1f3fc_200d_2642_fe0f", R.drawable.emoji_1f6b4_1f3fc_200d_2642_fe0f); // }, new String[0], 35, 19, false,
        sEmojisMap.put("1f6b4_1f3fd_200d_2642_fe0f", R.drawable.emoji_1f6b4_1f3fd_200d_2642_fe0f); // }, new String[0], 35, 20, false,
        sEmojisMap.put("1f6b4_1f3fe_200d_2642_fe0f", R.drawable.emoji_1f6b4_1f3fe_200d_2642_fe0f); // }, new String[0], 35, 21, false,
        sEmojisMap.put("1f6b4_1f3ff_200d_2642_fe0f", R.drawable.emoji_1f6b4_1f3ff_200d_2642_fe0f); // }, new String[0], 35, 22, false
        sEmojisMap.put("1f6b4_200d_2640_fe0f", R.drawable.emoji_1f6b4_200d_2640_fe0f); // woman-biking
        sEmojisMap.put("1f6b4_1f3fb_200d_2640_fe0f", R.drawable.emoji_1f6b4_1f3fb_200d_2640_fe0f); // }, new String[0], 35, 12, false,
        sEmojisMap.put("1f6b4_1f3fc_200d_2640_fe0f", R.drawable.emoji_1f6b4_1f3fc_200d_2640_fe0f); // }, new String[0], 35, 13, false,
        sEmojisMap.put("1f6b4_1f3fd_200d_2640_fe0f", R.drawable.emoji_1f6b4_1f3fd_200d_2640_fe0f); // }, new String[0], 35, 14, false,
        sEmojisMap.put("1f6b4_1f3fe_200d_2640_fe0f", R.drawable.emoji_1f6b4_1f3fe_200d_2640_fe0f); // }, new String[0], 35, 15, false,
        sEmojisMap.put("1f6b4_1f3ff_200d_2640_fe0f", R.drawable.emoji_1f6b4_1f3ff_200d_2640_fe0f); // }, new String[0], 35, 16, false
        sEmojisMap.put("1f6b5", R.drawable.emoji_1f6b5); // mountain_bicyclist
        sEmojisMap.put("1f6b5_1f3fb", R.drawable.emoji_1f6b5_1f3fb); // }, new String[0], 35, 42, false,
        sEmojisMap.put("1f6b5_1f3fc", R.drawable.emoji_1f6b5_1f3fc); // }, new String[0], 35, 43, false,
        sEmojisMap.put("1f6b5_1f3fd", R.drawable.emoji_1f6b5_1f3fd); // }, new String[0], 35, 44, false,
        sEmojisMap.put("1f6b5_1f3fe", R.drawable.emoji_1f6b5_1f3fe); // }, new String[0], 35, 45, false,
        sEmojisMap.put("1f6b5_1f3ff", R.drawable.emoji_1f6b5_1f3ff); // }, new String[0], 35, 46, false
        sEmojisMap.put("1f6b5_200d_2642_fe0f", R.drawable.emoji_1f6b5_200d_2642_fe0f); // man-mountain-biking
        sEmojisMap.put("1f6b5_1f3fb_200d_2642_fe0f", R.drawable.emoji_1f6b5_1f3fb_200d_2642_fe0f); // }, new String[0], 35, 36, false,
        sEmojisMap.put("1f6b5_1f3fc_200d_2642_fe0f", R.drawable.emoji_1f6b5_1f3fc_200d_2642_fe0f); // }, new String[0], 35, 37, false,
        sEmojisMap.put("1f6b5_1f3fd_200d_2642_fe0f", R.drawable.emoji_1f6b5_1f3fd_200d_2642_fe0f); // }, new String[0], 35, 38, false,
        sEmojisMap.put("1f6b5_1f3fe_200d_2642_fe0f", R.drawable.emoji_1f6b5_1f3fe_200d_2642_fe0f); // }, new String[0], 35, 39, false,
        sEmojisMap.put("1f6b5_1f3ff_200d_2642_fe0f", R.drawable.emoji_1f6b5_1f3ff_200d_2642_fe0f); // }, new String[0], 35, 40, false
        sEmojisMap.put("1f6b5_200d_2640_fe0f", R.drawable.emoji_1f6b5_200d_2640_fe0f); // woman-mountain-biking
        sEmojisMap.put("1f6b5_1f3fb_200d_2640_fe0f", R.drawable.emoji_1f6b5_1f3fb_200d_2640_fe0f); // }, new String[0], 35, 30, false,
        sEmojisMap.put("1f6b5_1f3fc_200d_2640_fe0f", R.drawable.emoji_1f6b5_1f3fc_200d_2640_fe0f); // }, new String[0], 35, 31, false,
        sEmojisMap.put("1f6b5_1f3fd_200d_2640_fe0f", R.drawable.emoji_1f6b5_1f3fd_200d_2640_fe0f); // }, new String[0], 35, 32, false,
        sEmojisMap.put("1f6b5_1f3fe_200d_2640_fe0f", R.drawable.emoji_1f6b5_1f3fe_200d_2640_fe0f); // }, new String[0], 35, 33, false,
        sEmojisMap.put("1f6b5_1f3ff_200d_2640_fe0f", R.drawable.emoji_1f6b5_1f3ff_200d_2640_fe0f); // }, new String[0], 35, 34, false
        sEmojisMap.put("1f938", R.drawable.emoji_1f938); // person_doing_cartwheel
        sEmojisMap.put("1f938_1f3fb", R.drawable.emoji_1f938_1f3fb); // }, new String[0], 40, 9, false,
        sEmojisMap.put("1f938_1f3fc", R.drawable.emoji_1f938_1f3fc); // }, new String[0], 40, 10, false,
        sEmojisMap.put("1f938_1f3fd", R.drawable.emoji_1f938_1f3fd); // }, new String[0], 40, 11, false,
        sEmojisMap.put("1f938_1f3fe", R.drawable.emoji_1f938_1f3fe); // }, new String[0], 40, 12, false,
        sEmojisMap.put("1f938_1f3ff", R.drawable.emoji_1f938_1f3ff); // }, new String[0], 40, 13, false
        sEmojisMap.put("1f938_200d_2642_fe0f", R.drawable.emoji_1f938_200d_2642_fe0f); // man-cartwheeling
        sEmojisMap.put("1f938_1f3fb_200d_2642_fe0f", R.drawable.emoji_1f938_1f3fb_200d_2642_fe0f); // }, new String[0], 40, 3, false,
        sEmojisMap.put("1f938_1f3fc_200d_2642_fe0f", R.drawable.emoji_1f938_1f3fc_200d_2642_fe0f); // }, new String[0], 40, 4, false,
        sEmojisMap.put("1f938_1f3fd_200d_2642_fe0f", R.drawable.emoji_1f938_1f3fd_200d_2642_fe0f); // }, new String[0], 40, 5, false,
        sEmojisMap.put("1f938_1f3fe_200d_2642_fe0f", R.drawable.emoji_1f938_1f3fe_200d_2642_fe0f); // }, new String[0], 40, 6, false,
        sEmojisMap.put("1f938_1f3ff_200d_2642_fe0f", R.drawable.emoji_1f938_1f3ff_200d_2642_fe0f); // }, new String[0], 40, 7, false
        sEmojisMap.put("1f938_200d_2640_fe0f", R.drawable.emoji_1f938_200d_2640_fe0f); // woman-cartwheeling
        sEmojisMap.put("1f938_1f3fb_200d_2640_fe0f", R.drawable.emoji_1f938_1f3fb_200d_2640_fe0f); // }, new String[0], 39, 54, false,
        sEmojisMap.put("1f938_1f3fc_200d_2640_fe0f", R.drawable.emoji_1f938_1f3fc_200d_2640_fe0f); // }, new String[0], 39, 55, false,
        sEmojisMap.put("1f938_1f3fd_200d_2640_fe0f", R.drawable.emoji_1f938_1f3fd_200d_2640_fe0f); // }, new String[0], 39, 56, false,
        sEmojisMap.put("1f938_1f3fe_200d_2640_fe0f", R.drawable.emoji_1f938_1f3fe_200d_2640_fe0f); // }, new String[0], 40, 0, false,
        sEmojisMap.put("1f938_1f3ff_200d_2640_fe0f", R.drawable.emoji_1f938_1f3ff_200d_2640_fe0f); // }, new String[0], 40, 1, false
        sEmojisMap.put("1f93c", R.drawable.emoji_1f93c); // wrestlers
        sEmojisMap.put("1f93c_200d_2642_fe0f", R.drawable.emoji_1f93c_200d_2642_fe0f); // man-wrestling
        sEmojisMap.put("1f93c_200d_2640_fe0f", R.drawable.emoji_1f93c_200d_2640_fe0f); // woman-wrestling
        sEmojisMap.put("1f93d", R.drawable.emoji_1f93d); // water_polo
        sEmojisMap.put("1f93d_1f3fb", R.drawable.emoji_1f93d_1f3fb); // }, new String[0], 40, 49, false,
        sEmojisMap.put("1f93d_1f3fc", R.drawable.emoji_1f93d_1f3fc); // }, new String[0], 40, 50, false,
        sEmojisMap.put("1f93d_1f3fd", R.drawable.emoji_1f93d_1f3fd); // }, new String[0], 40, 51, false,
        sEmojisMap.put("1f93d_1f3fe", R.drawable.emoji_1f93d_1f3fe); // }, new String[0], 40, 52, false,
        sEmojisMap.put("1f93d_1f3ff", R.drawable.emoji_1f93d_1f3ff); // }, new String[0], 40, 53, false
        sEmojisMap.put("1f93d_200d_2642_fe0f", R.drawable.emoji_1f93d_200d_2642_fe0f); // man-playing-water-polo
        sEmojisMap.put("1f93d_1f3fb_200d_2642_fe0f", R.drawable.emoji_1f93d_1f3fb_200d_2642_fe0f); // }, new String[0], 40, 43, false,
        sEmojisMap.put("1f93d_1f3fc_200d_2642_fe0f", R.drawable.emoji_1f93d_1f3fc_200d_2642_fe0f); // }, new String[0], 40, 44, false,
        sEmojisMap.put("1f93d_1f3fd_200d_2642_fe0f", R.drawable.emoji_1f93d_1f3fd_200d_2642_fe0f); // }, new String[0], 40, 45, false,
        sEmojisMap.put("1f93d_1f3fe_200d_2642_fe0f", R.drawable.emoji_1f93d_1f3fe_200d_2642_fe0f); // }, new String[0], 40, 46, false,
        sEmojisMap.put("1f93d_1f3ff_200d_2642_fe0f", R.drawable.emoji_1f93d_1f3ff_200d_2642_fe0f); // }, new String[0], 40, 47, false
        sEmojisMap.put("1f93d_200d_2640_fe0f", R.drawable.emoji_1f93d_200d_2640_fe0f); // woman-playing-water-polo
        sEmojisMap.put("1f93d_1f3fb_200d_2640_fe0f", R.drawable.emoji_1f93d_1f3fb_200d_2640_fe0f); // }, new String[0], 40, 37, false,
        sEmojisMap.put("1f93d_1f3fc_200d_2640_fe0f", R.drawable.emoji_1f93d_1f3fc_200d_2640_fe0f); // }, new String[0], 40, 38, false,
        sEmojisMap.put("1f93d_1f3fd_200d_2640_fe0f", R.drawable.emoji_1f93d_1f3fd_200d_2640_fe0f); // }, new String[0], 40, 39, false,
        sEmojisMap.put("1f93d_1f3fe_200d_2640_fe0f", R.drawable.emoji_1f93d_1f3fe_200d_2640_fe0f); // }, new String[0], 40, 40, false,
        sEmojisMap.put("1f93d_1f3ff_200d_2640_fe0f", R.drawable.emoji_1f93d_1f3ff_200d_2640_fe0f); // }, new String[0], 40, 41, false
        sEmojisMap.put("1f93e", R.drawable.emoji_1f93e); // handball
        sEmojisMap.put("1f93e_1f3fb", R.drawable.emoji_1f93e_1f3fb); // }, new String[0], 41, 10, false,
        sEmojisMap.put("1f93e_1f3fc", R.drawable.emoji_1f93e_1f3fc); // }, new String[0], 41, 11, false,
        sEmojisMap.put("1f93e_1f3fd", R.drawable.emoji_1f93e_1f3fd); // }, new String[0], 41, 12, false,
        sEmojisMap.put("1f93e_1f3fe", R.drawable.emoji_1f93e_1f3fe); // }, new String[0], 41, 13, false,
        sEmojisMap.put("1f93e_1f3ff", R.drawable.emoji_1f93e_1f3ff); // }, new String[0], 41, 14, false
        sEmojisMap.put("1f93e_200d_2642_fe0f", R.drawable.emoji_1f93e_200d_2642_fe0f); // man-playing-handball
        sEmojisMap.put("1f93e_1f3fb_200d_2642_fe0f", R.drawable.emoji_1f93e_1f3fb_200d_2642_fe0f); // }, new String[0], 41, 4, false,
        sEmojisMap.put("1f93e_1f3fc_200d_2642_fe0f", R.drawable.emoji_1f93e_1f3fc_200d_2642_fe0f); // }, new String[0], 41, 5, false,
        sEmojisMap.put("1f93e_1f3fd_200d_2642_fe0f", R.drawable.emoji_1f93e_1f3fd_200d_2642_fe0f); // }, new String[0], 41, 6, false,
        sEmojisMap.put("1f93e_1f3fe_200d_2642_fe0f", R.drawable.emoji_1f93e_1f3fe_200d_2642_fe0f); // }, new String[0], 41, 7, false,
        sEmojisMap.put("1f93e_1f3ff_200d_2642_fe0f", R.drawable.emoji_1f93e_1f3ff_200d_2642_fe0f); // }, new String[0], 41, 8, false
        sEmojisMap.put("1f93e_200d_2640_fe0f", R.drawable.emoji_1f93e_200d_2640_fe0f); // woman-playing-handball
        sEmojisMap.put("1f93e_1f3fb_200d_2640_fe0f", R.drawable.emoji_1f93e_1f3fb_200d_2640_fe0f); // }, new String[0], 40, 55, false,
        sEmojisMap.put("1f93e_1f3fc_200d_2640_fe0f", R.drawable.emoji_1f93e_1f3fc_200d_2640_fe0f); // }, new String[0], 40, 56, false,
        sEmojisMap.put("1f93e_1f3fd_200d_2640_fe0f", R.drawable.emoji_1f93e_1f3fd_200d_2640_fe0f); // }, new String[0], 41, 0, false,
        sEmojisMap.put("1f93e_1f3fe_200d_2640_fe0f", R.drawable.emoji_1f93e_1f3fe_200d_2640_fe0f); // }, new String[0], 41, 1, false,
        sEmojisMap.put("1f93e_1f3ff_200d_2640_fe0f", R.drawable.emoji_1f93e_1f3ff_200d_2640_fe0f); // }, new String[0], 41, 2, false
        sEmojisMap.put("1f939", R.drawable.emoji_1f939); // juggling
        sEmojisMap.put("1f939_1f3fb", R.drawable.emoji_1f939_1f3fb); // }, new String[0], 40, 27, false,
        sEmojisMap.put("1f939_1f3fc", R.drawable.emoji_1f939_1f3fc); // }, new String[0], 40, 28, false,
        sEmojisMap.put("1f939_1f3fd", R.drawable.emoji_1f939_1f3fd); // }, new String[0], 40, 29, false,
        sEmojisMap.put("1f939_1f3fe", R.drawable.emoji_1f939_1f3fe); // }, new String[0], 40, 30, false,
        sEmojisMap.put("1f939_1f3ff", R.drawable.emoji_1f939_1f3ff); // }, new String[0], 40, 31, false
        sEmojisMap.put("1f939_200d_2642_fe0f", R.drawable.emoji_1f939_200d_2642_fe0f); // man-juggling
        sEmojisMap.put("1f939_1f3fb_200d_2642_fe0f", R.drawable.emoji_1f939_1f3fb_200d_2642_fe0f); // }, new String[0], 40, 21, false,
        sEmojisMap.put("1f939_1f3fc_200d_2642_fe0f", R.drawable.emoji_1f939_1f3fc_200d_2642_fe0f); // }, new String[0], 40, 22, false,
        sEmojisMap.put("1f939_1f3fd_200d_2642_fe0f", R.drawable.emoji_1f939_1f3fd_200d_2642_fe0f); // }, new String[0], 40, 23, false,
        sEmojisMap.put("1f939_1f3fe_200d_2642_fe0f", R.drawable.emoji_1f939_1f3fe_200d_2642_fe0f); // }, new String[0], 40, 24, false,
        sEmojisMap.put("1f939_1f3ff_200d_2642_fe0f", R.drawable.emoji_1f939_1f3ff_200d_2642_fe0f); // }, new String[0], 40, 25, false
        sEmojisMap.put("1f939_200d_2640_fe0f", R.drawable.emoji_1f939_200d_2640_fe0f); // woman-juggling
        sEmojisMap.put("1f939_1f3fb_200d_2640_fe0f", R.drawable.emoji_1f939_1f3fb_200d_2640_fe0f); // }, new String[0], 40, 15, false,
        sEmojisMap.put("1f939_1f3fc_200d_2640_fe0f", R.drawable.emoji_1f939_1f3fc_200d_2640_fe0f); // }, new String[0], 40, 16, false,
        sEmojisMap.put("1f939_1f3fd_200d_2640_fe0f", R.drawable.emoji_1f939_1f3fd_200d_2640_fe0f); // }, new String[0], 40, 17, false,
        sEmojisMap.put("1f939_1f3fe_200d_2640_fe0f", R.drawable.emoji_1f939_1f3fe_200d_2640_fe0f); // }, new String[0], 40, 18, false,
        sEmojisMap.put("1f939_1f3ff_200d_2640_fe0f", R.drawable.emoji_1f939_1f3ff_200d_2640_fe0f); // }, new String[0], 40, 19, false
        sEmojisMap.put("1f9d8", R.drawable.emoji_1f9d8); // person_in_lotus_position
        sEmojisMap.put("1f9d8_1f3fb", R.drawable.emoji_1f9d8_1f3fb); // }, new String[0], 49, 32, true,
        sEmojisMap.put("1f9d8_1f3fc", R.drawable.emoji_1f9d8_1f3fc); // }, new String[0], 49, 33, true,
        sEmojisMap.put("1f9d8_1f3fd", R.drawable.emoji_1f9d8_1f3fd); // }, new String[0], 49, 34, true,
        sEmojisMap.put("1f9d8_1f3fe", R.drawable.emoji_1f9d8_1f3fe); // }, new String[0], 49, 35, true,
        sEmojisMap.put("1f9d8_1f3ff", R.drawable.emoji_1f9d8_1f3ff); // }, new String[0], 49, 36, true
        sEmojisMap.put("1f9d8_200d_2642_fe0f", R.drawable.emoji_1f9d8_200d_2642_fe0f); // man_in_lotus_position
        sEmojisMap.put("1f9d8_1f3fb_200d_2642_fe0f", R.drawable.emoji_1f9d8_1f3fb_200d_2642_fe0f); // }, new String[0], 49, 26, false,
        sEmojisMap.put("1f9d8_1f3fc_200d_2642_fe0f", R.drawable.emoji_1f9d8_1f3fc_200d_2642_fe0f); // }, new String[0], 49, 27, false,
        sEmojisMap.put("1f9d8_1f3fd_200d_2642_fe0f", R.drawable.emoji_1f9d8_1f3fd_200d_2642_fe0f); // }, new String[0], 49, 28, false,
        sEmojisMap.put("1f9d8_1f3fe_200d_2642_fe0f", R.drawable.emoji_1f9d8_1f3fe_200d_2642_fe0f); // }, new String[0], 49, 29, false,
        sEmojisMap.put("1f9d8_1f3ff_200d_2642_fe0f", R.drawable.emoji_1f9d8_1f3ff_200d_2642_fe0f); // }, new String[0], 49, 30, false
        sEmojisMap.put("1f9d8_200d_2640_fe0f", R.drawable.emoji_1f9d8_200d_2640_fe0f); // woman_in_lotus_position
        sEmojisMap.put("1f9d8_1f3fb_200d_2640_fe0f", R.drawable.emoji_1f9d8_1f3fb_200d_2640_fe0f); // }, new String[0], 49, 20, false,
        sEmojisMap.put("1f9d8_1f3fc_200d_2640_fe0f", R.drawable.emoji_1f9d8_1f3fc_200d_2640_fe0f); // }, new String[0], 49, 21, false,
        sEmojisMap.put("1f9d8_1f3fd_200d_2640_fe0f", R.drawable.emoji_1f9d8_1f3fd_200d_2640_fe0f); // }, new String[0], 49, 22, false,
        sEmojisMap.put("1f9d8_1f3fe_200d_2640_fe0f", R.drawable.emoji_1f9d8_1f3fe_200d_2640_fe0f); // }, new String[0], 49, 23, false,
        sEmojisMap.put("1f9d8_1f3ff_200d_2640_fe0f", R.drawable.emoji_1f9d8_1f3ff_200d_2640_fe0f); // }, new String[0], 49, 24, false
        sEmojisMap.put("1f6c0", R.drawable.emoji_1f6c0); // bath
        sEmojisMap.put("1f6c0_1f3fb", R.drawable.emoji_1f6c0_1f3fb); // }, new String[0], 36, 18, false,
        sEmojisMap.put("1f6c0_1f3fc", R.drawable.emoji_1f6c0_1f3fc); // }, new String[0], 36, 19, false,
        sEmojisMap.put("1f6c0_1f3fd", R.drawable.emoji_1f6c0_1f3fd); // }, new String[0], 36, 20, false,
        sEmojisMap.put("1f6c0_1f3fe", R.drawable.emoji_1f6c0_1f3fe); // }, new String[0], 36, 21, false,
        sEmojisMap.put("1f6c0_1f3ff", R.drawable.emoji_1f6c0_1f3ff); // }, new String[0], 36, 22, false
        sEmojisMap.put("1f6cc", R.drawable.emoji_1f6cc); // sleeping_accommodation
        sEmojisMap.put("1f6cc_1f3fb", R.drawable.emoji_1f6cc_1f3fb); // }, new String[0], 36, 30, false,
        sEmojisMap.put("1f6cc_1f3fc", R.drawable.emoji_1f6cc_1f3fc); // }, new String[0], 36, 31, false,
        sEmojisMap.put("1f6cc_1f3fd", R.drawable.emoji_1f6cc_1f3fd); // }, new String[0], 36, 32, false,
        sEmojisMap.put("1f6cc_1f3fe", R.drawable.emoji_1f6cc_1f3fe); // }, new String[0], 36, 33, false,
        sEmojisMap.put("1f6cc_1f3ff", R.drawable.emoji_1f6cc_1f3ff); // }, new String[0], 36, 34, false
        sEmojisMap.put("1f9d1_200d_1f91d_200d_1f9d1", R.drawable.emoji_1f9d1_200d_1f91d_200d_1f9d1); // people_holding_hands
        sEmojisMap.put("1f9d1_1f3fb_200d_1f91d_200d_1f9d1_1f3fb", R.drawable.emoji_1f9d1_1f3fb_200d_1f91d_200d_1f9d1_1f3fb); // }, new String[0], 46, 39, false,
        sEmojisMap.put("1f9d1_1f3fc_200d_1f91d_200d_1f9d1_1f3fb", R.drawable.emoji_1f9d1_1f3fc_200d_1f91d_200d_1f9d1_1f3fb); // }, new String[0], 46, 44, false,
        sEmojisMap.put("1f9d1_1f3fc_200d_1f91d_200d_1f9d1_1f3fc", R.drawable.emoji_1f9d1_1f3fc_200d_1f91d_200d_1f9d1_1f3fc); // }, new String[0], 46, 45, false,
        sEmojisMap.put("1f9d1_1f3fd_200d_1f91d_200d_1f9d1_1f3fb", R.drawable.emoji_1f9d1_1f3fd_200d_1f91d_200d_1f9d1_1f3fb); // }, new String[0], 46, 49, false,
        sEmojisMap.put("1f9d1_1f3fd_200d_1f91d_200d_1f9d1_1f3fc", R.drawable.emoji_1f9d1_1f3fd_200d_1f91d_200d_1f9d1_1f3fc); // }, new String[0], 46, 50, false,
        sEmojisMap.put("1f9d1_1f3fd_200d_1f91d_200d_1f9d1_1f3fd", R.drawable.emoji_1f9d1_1f3fd_200d_1f91d_200d_1f9d1_1f3fd); // }, new String[0], 46, 51, false,
        sEmojisMap.put("1f9d1_1f3fe_200d_1f91d_200d_1f9d1_1f3fb", R.drawable.emoji_1f9d1_1f3fe_200d_1f91d_200d_1f9d1_1f3fb); // }, new String[0], 46, 54, false,
        sEmojisMap.put("1f9d1_1f3fe_200d_1f91d_200d_1f9d1_1f3fc", R.drawable.emoji_1f9d1_1f3fe_200d_1f91d_200d_1f9d1_1f3fc); // }, new String[0], 46, 55, false,
        sEmojisMap.put("1f9d1_1f3fe_200d_1f91d_200d_1f9d1_1f3fd", R.drawable.emoji_1f9d1_1f3fe_200d_1f91d_200d_1f9d1_1f3fd); // }, new String[0], 46, 56, false,
        sEmojisMap.put("1f9d1_1f3fe_200d_1f91d_200d_1f9d1_1f3fe", R.drawable.emoji_1f9d1_1f3fe_200d_1f91d_200d_1f9d1_1f3fe); // }, new String[0], 47, 0, false,
        sEmojisMap.put("1f9d1_1f3ff_200d_1f91d_200d_1f9d1_1f3fb", R.drawable.emoji_1f9d1_1f3ff_200d_1f91d_200d_1f9d1_1f3fb); // }, new String[0], 47, 2, false,
        sEmojisMap.put("1f9d1_1f3ff_200d_1f91d_200d_1f9d1_1f3fc", R.drawable.emoji_1f9d1_1f3ff_200d_1f91d_200d_1f9d1_1f3fc); // }, new String[0], 47, 3, false,
        sEmojisMap.put("1f9d1_1f3ff_200d_1f91d_200d_1f9d1_1f3fd", R.drawable.emoji_1f9d1_1f3ff_200d_1f91d_200d_1f9d1_1f3fd); // }, new String[0], 47, 4, false,
        sEmojisMap.put("1f9d1_1f3ff_200d_1f91d_200d_1f9d1_1f3fe", R.drawable.emoji_1f9d1_1f3ff_200d_1f91d_200d_1f9d1_1f3fe); // }, new String[0], 47, 5, false,
        sEmojisMap.put("1f9d1_1f3ff_200d_1f91d_200d_1f9d1_1f3ff", R.drawable.emoji_1f9d1_1f3ff_200d_1f91d_200d_1f9d1_1f3ff); // }, new String[0], 47, 6, false
        sEmojisMap.put("1f46d", R.drawable.emoji_1f46d); // two_women_holding_hands|women_holding_hands
        sEmojisMap.put("1f46d_1f3fb", R.drawable.emoji_1f46d_1f3fb); // }, new String[0], 21, 12, false,
        sEmojisMap.put("1f46d_1f3fc", R.drawable.emoji_1f46d_1f3fc); // }, new String[0], 21, 13, false,
        sEmojisMap.put("1f46d_1f3fd", R.drawable.emoji_1f46d_1f3fd); // }, new String[0], 21, 14, false,
        sEmojisMap.put("1f46d_1f3fe", R.drawable.emoji_1f46d_1f3fe); // }, new String[0], 21, 15, false,
        sEmojisMap.put("1f46d_1f3ff", R.drawable.emoji_1f46d_1f3ff); // }, new String[0], 21, 16, false,
        sEmojisMap.put("1f469_1f3fc_200d_1f91d_200d_1f469_1f3fb", R.drawable.emoji_1f469_1f3fc_200d_1f91d_200d_1f469_1f3fb); // }, new String[0], 21, 21, false,
        sEmojisMap.put("1f469_1f3fd_200d_1f91d_200d_1f469_1f3fb", R.drawable.emoji_1f469_1f3fd_200d_1f91d_200d_1f469_1f3fb); // }, new String[0], 21, 25, false,
        sEmojisMap.put("1f469_1f3fd_200d_1f91d_200d_1f469_1f3fc", R.drawable.emoji_1f469_1f3fd_200d_1f91d_200d_1f469_1f3fc); // }, new String[0], 21, 26, false,
        sEmojisMap.put("1f469_1f3fe_200d_1f91d_200d_1f469_1f3fb", R.drawable.emoji_1f469_1f3fe_200d_1f91d_200d_1f469_1f3fb); // }, new String[0], 21, 29, false,
        sEmojisMap.put("1f469_1f3fe_200d_1f91d_200d_1f469_1f3fc", R.drawable.emoji_1f469_1f3fe_200d_1f91d_200d_1f469_1f3fc); // }, new String[0], 21, 30, false,
        sEmojisMap.put("1f469_1f3fe_200d_1f91d_200d_1f469_1f3fd", R.drawable.emoji_1f469_1f3fe_200d_1f91d_200d_1f469_1f3fd); // }, new String[0], 21, 31, false,
        sEmojisMap.put("1f469_1f3ff_200d_1f91d_200d_1f469_1f3fb", R.drawable.emoji_1f469_1f3ff_200d_1f91d_200d_1f469_1f3fb); // }, new String[0], 21, 33, false,
        sEmojisMap.put("1f469_1f3ff_200d_1f91d_200d_1f469_1f3fc", R.drawable.emoji_1f469_1f3ff_200d_1f91d_200d_1f469_1f3fc); // }, new String[0], 21, 34, false,
        sEmojisMap.put("1f469_1f3ff_200d_1f91d_200d_1f469_1f3fd", R.drawable.emoji_1f469_1f3ff_200d_1f91d_200d_1f469_1f3fd); // }, new String[0], 21, 35, false,
        sEmojisMap.put("1f469_1f3ff_200d_1f91d_200d_1f469_1f3fe", R.drawable.emoji_1f469_1f3ff_200d_1f91d_200d_1f469_1f3fe); // }, new String[0], 21, 36, false
        sEmojisMap.put("1f46b", R.drawable.emoji_1f46b); // couple|man_and_woman_holding_hands|woman_and_man_holding_hands
        sEmojisMap.put("1f46b_1f3fb", R.drawable.emoji_1f46b_1f3fb); // }, new String[0], 20, 17, false,
        sEmojisMap.put("1f46b_1f3fc", R.drawable.emoji_1f46b_1f3fc); // }, new String[0], 20, 18, false,
        sEmojisMap.put("1f46b_1f3fd", R.drawable.emoji_1f46b_1f3fd); // }, new String[0], 20, 19, false,
        sEmojisMap.put("1f46b_1f3fe", R.drawable.emoji_1f46b_1f3fe); // }, new String[0], 20, 20, false,
        sEmojisMap.put("1f46b_1f3ff", R.drawable.emoji_1f46b_1f3ff); // }, new String[0], 20, 21, false,
        sEmojisMap.put("1f469_1f3fb_200d_1f91d_200d_1f468_1f3fc", R.drawable.emoji_1f469_1f3fb_200d_1f91d_200d_1f468_1f3fc); // }, new String[0], 20, 22, false,
        sEmojisMap.put("1f469_1f3fb_200d_1f91d_200d_1f468_1f3fd", R.drawable.emoji_1f469_1f3fb_200d_1f91d_200d_1f468_1f3fd); // }, new String[0], 20, 23, false,
        sEmojisMap.put("1f469_1f3fb_200d_1f91d_200d_1f468_1f3fe", R.drawable.emoji_1f469_1f3fb_200d_1f91d_200d_1f468_1f3fe); // }, new String[0], 20, 24, false,
        sEmojisMap.put("1f469_1f3fb_200d_1f91d_200d_1f468_1f3ff", R.drawable.emoji_1f469_1f3fb_200d_1f91d_200d_1f468_1f3ff); // }, new String[0], 20, 25, false,
        sEmojisMap.put("1f469_1f3fc_200d_1f91d_200d_1f468_1f3fb", R.drawable.emoji_1f469_1f3fc_200d_1f91d_200d_1f468_1f3fb); // }, new String[0], 20, 26, false,
        sEmojisMap.put("1f469_1f3fc_200d_1f91d_200d_1f468_1f3fd", R.drawable.emoji_1f469_1f3fc_200d_1f91d_200d_1f468_1f3fd); // }, new String[0], 20, 27, false,
        sEmojisMap.put("1f469_1f3fc_200d_1f91d_200d_1f468_1f3fe", R.drawable.emoji_1f469_1f3fc_200d_1f91d_200d_1f468_1f3fe); // }, new String[0], 20, 28, false,
        sEmojisMap.put("1f469_1f3fc_200d_1f91d_200d_1f468_1f3ff", R.drawable.emoji_1f469_1f3fc_200d_1f91d_200d_1f468_1f3ff); // }, new String[0], 20, 29, false,
        sEmojisMap.put("1f469_1f3fd_200d_1f91d_200d_1f468_1f3fb", R.drawable.emoji_1f469_1f3fd_200d_1f91d_200d_1f468_1f3fb); // }, new String[0], 20, 30, false,
        sEmojisMap.put("1f469_1f3fd_200d_1f91d_200d_1f468_1f3fc", R.drawable.emoji_1f469_1f3fd_200d_1f91d_200d_1f468_1f3fc); // }, new String[0], 20, 31, false,
        sEmojisMap.put("1f469_1f3fd_200d_1f91d_200d_1f468_1f3fe", R.drawable.emoji_1f469_1f3fd_200d_1f91d_200d_1f468_1f3fe); // }, new String[0], 20, 32, false,
        sEmojisMap.put("1f469_1f3fd_200d_1f91d_200d_1f468_1f3ff", R.drawable.emoji_1f469_1f3fd_200d_1f91d_200d_1f468_1f3ff); // }, new String[0], 20, 33, false,
        sEmojisMap.put("1f469_1f3fe_200d_1f91d_200d_1f468_1f3fb", R.drawable.emoji_1f469_1f3fe_200d_1f91d_200d_1f468_1f3fb); // }, new String[0], 20, 34, false,
        sEmojisMap.put("1f469_1f3fe_200d_1f91d_200d_1f468_1f3fc", R.drawable.emoji_1f469_1f3fe_200d_1f91d_200d_1f468_1f3fc); // }, new String[0], 20, 35, false,
        sEmojisMap.put("1f469_1f3fe_200d_1f91d_200d_1f468_1f3fd", R.drawable.emoji_1f469_1f3fe_200d_1f91d_200d_1f468_1f3fd); // }, new String[0], 20, 36, false,
        sEmojisMap.put("1f469_1f3fe_200d_1f91d_200d_1f468_1f3ff", R.drawable.emoji_1f469_1f3fe_200d_1f91d_200d_1f468_1f3ff); // }, new String[0], 20, 37, false,
        sEmojisMap.put("1f469_1f3ff_200d_1f91d_200d_1f468_1f3fb", R.drawable.emoji_1f469_1f3ff_200d_1f91d_200d_1f468_1f3fb); // }, new String[0], 20, 38, false,
        sEmojisMap.put("1f469_1f3ff_200d_1f91d_200d_1f468_1f3fc", R.drawable.emoji_1f469_1f3ff_200d_1f91d_200d_1f468_1f3fc); // }, new String[0], 20, 39, false,
        sEmojisMap.put("1f469_1f3ff_200d_1f91d_200d_1f468_1f3fd", R.drawable.emoji_1f469_1f3ff_200d_1f91d_200d_1f468_1f3fd); // }, new String[0], 20, 40, false,
        sEmojisMap.put("1f469_1f3ff_200d_1f91d_200d_1f468_1f3fe", R.drawable.emoji_1f469_1f3ff_200d_1f91d_200d_1f468_1f3fe); // }, new String[0], 20, 41, false
        sEmojisMap.put("1f46c", R.drawable.emoji_1f46c); // two_men_holding_hands|men_holding_hands
        sEmojisMap.put("1f46c_1f3fb", R.drawable.emoji_1f46c_1f3fb); // }, new String[0], 20, 43, false,
        sEmojisMap.put("1f46c_1f3fc", R.drawable.emoji_1f46c_1f3fc); // }, new String[0], 20, 44, false,
        sEmojisMap.put("1f46c_1f3fd", R.drawable.emoji_1f46c_1f3fd); // }, new String[0], 20, 45, false,
        sEmojisMap.put("1f46c_1f3fe", R.drawable.emoji_1f46c_1f3fe); // }, new String[0], 20, 46, false,
        sEmojisMap.put("1f46c_1f3ff", R.drawable.emoji_1f46c_1f3ff); // }, new String[0], 20, 47, false,
        sEmojisMap.put("1f468_1f3fc_200d_1f91d_200d_1f468_1f3fb", R.drawable.emoji_1f468_1f3fc_200d_1f91d_200d_1f468_1f3fb); // }, new String[0], 20, 52, false,
        sEmojisMap.put("1f468_1f3fd_200d_1f91d_200d_1f468_1f3fb", R.drawable.emoji_1f468_1f3fd_200d_1f91d_200d_1f468_1f3fb); // }, new String[0], 20, 56, false,
        sEmojisMap.put("1f468_1f3fd_200d_1f91d_200d_1f468_1f3fc", R.drawable.emoji_1f468_1f3fd_200d_1f91d_200d_1f468_1f3fc); // }, new String[0], 21, 0, false,
        sEmojisMap.put("1f468_1f3fe_200d_1f91d_200d_1f468_1f3fb", R.drawable.emoji_1f468_1f3fe_200d_1f91d_200d_1f468_1f3fb); // }, new String[0], 21, 3, false,
        sEmojisMap.put("1f468_1f3fe_200d_1f91d_200d_1f468_1f3fc", R.drawable.emoji_1f468_1f3fe_200d_1f91d_200d_1f468_1f3fc); // }, new String[0], 21, 4, false,
        sEmojisMap.put("1f468_1f3fe_200d_1f91d_200d_1f468_1f3fd", R.drawable.emoji_1f468_1f3fe_200d_1f91d_200d_1f468_1f3fd); // }, new String[0], 21, 5, false,
        sEmojisMap.put("1f468_1f3ff_200d_1f91d_200d_1f468_1f3fb", R.drawable.emoji_1f468_1f3ff_200d_1f91d_200d_1f468_1f3fb); // }, new String[0], 21, 7, false,
        sEmojisMap.put("1f468_1f3ff_200d_1f91d_200d_1f468_1f3fc", R.drawable.emoji_1f468_1f3ff_200d_1f91d_200d_1f468_1f3fc); // }, new String[0], 21, 8, false,
        sEmojisMap.put("1f468_1f3ff_200d_1f91d_200d_1f468_1f3fd", R.drawable.emoji_1f468_1f3ff_200d_1f91d_200d_1f468_1f3fd); // }, new String[0], 21, 9, false,
        sEmojisMap.put("1f468_1f3ff_200d_1f91d_200d_1f468_1f3fe", R.drawable.emoji_1f468_1f3ff_200d_1f91d_200d_1f468_1f3fe); // }, new String[0], 21, 10, false
        sEmojisMap.put("1f48f", R.drawable.emoji_1f48f); // couplekiss
        sEmojisMap.put("1f469_200d_2764_200d_1f48b_200d_1f468", R.drawable.emoji_1f469_200d_2764_fe0f_200d_1f48b_200d_1f468); // woman-kiss-man
        sEmojisMap.put("1f468_200d_2764_200d_1f48b_200d_1f468", R.drawable.emoji_1f468_200d_2764_fe0f_200d_1f48b_200d_1f468); // man-kiss-man
        sEmojisMap.put("1f469_200d_2764_200d_1f48b_200d_1f469", R.drawable.emoji_1f469_200d_2764_fe0f_200d_1f48b_200d_1f469); // woman-kiss-woman
        sEmojisMap.put("1f491", R.drawable.emoji_1f491); // couple_with_heart
        sEmojisMap.put("1f469_200d_2764_200d_1f468", R.drawable.emoji_1f469_200d_2764_fe0f_200d_1f468); // woman-heart-man
        sEmojisMap.put("1f468_200d_2764_200d_1f468", R.drawable.emoji_1f468_200d_2764_fe0f_200d_1f468); // man-heart-man
        sEmojisMap.put("1f469_200d_2764_200d_1f469", R.drawable.emoji_1f469_200d_2764_fe0f_200d_1f469); // woman-heart-woman
        sEmojisMap.put("1f46a", R.drawable.emoji_1f46a); // family|man-woman-boy
        sEmojisMap.put("1f468_200d_1f469_200d_1f466", R.drawable.emoji_1f468_200d_1f469_200d_1f466); // man-woman-boy|family
        sEmojisMap.put("1f468_200d_1f469_200d_1f467", R.drawable.emoji_1f468_200d_1f469_200d_1f467); // man-woman-girl
        sEmojisMap.put("1f468_200d_1f469_200d_1f467_200d_1f466", R.drawable.emoji_1f468_200d_1f469_200d_1f467_200d_1f466); // man-woman-girl-boy
        sEmojisMap.put("1f468_200d_1f469_200d_1f466_200d_1f466", R.drawable.emoji_1f468_200d_1f469_200d_1f466_200d_1f466); // man-woman-boy-boy
        sEmojisMap.put("1f468_200d_1f469_200d_1f467_200d_1f467", R.drawable.emoji_1f468_200d_1f469_200d_1f467_200d_1f467); // man-woman-girl-girl
        sEmojisMap.put("1f468_200d_1f468_200d_1f466", R.drawable.emoji_1f468_200d_1f468_200d_1f466); // man-man-boy
        sEmojisMap.put("1f468_200d_1f468_200d_1f467", R.drawable.emoji_1f468_200d_1f468_200d_1f467); // man-man-girl
        sEmojisMap.put("1f468_200d_1f468_200d_1f467_200d_1f466", R.drawable.emoji_1f468_200d_1f468_200d_1f467_200d_1f466); // man-man-girl-boy
        sEmojisMap.put("1f468_200d_1f468_200d_1f466_200d_1f466", R.drawable.emoji_1f468_200d_1f468_200d_1f466_200d_1f466); // man-man-boy-boy
        sEmojisMap.put("1f468_200d_1f468_200d_1f467_200d_1f467", R.drawable.emoji_1f468_200d_1f468_200d_1f467_200d_1f467); // man-man-girl-girl
        sEmojisMap.put("1f469_200d_1f469_200d_1f466", R.drawable.emoji_1f469_200d_1f469_200d_1f466); // woman-woman-boy
        sEmojisMap.put("1f469_200d_1f469_200d_1f467", R.drawable.emoji_1f469_200d_1f469_200d_1f467); // woman-woman-girl
        sEmojisMap.put("1f469_200d_1f469_200d_1f467_200d_1f466", R.drawable.emoji_1f469_200d_1f469_200d_1f467_200d_1f466); // woman-woman-girl-boy
        sEmojisMap.put("1f469_200d_1f469_200d_1f466_200d_1f466", R.drawable.emoji_1f469_200d_1f469_200d_1f466_200d_1f466); // woman-woman-boy-boy
        sEmojisMap.put("1f469_200d_1f469_200d_1f467_200d_1f467", R.drawable.emoji_1f469_200d_1f469_200d_1f467_200d_1f467); // woman-woman-girl-girl
        sEmojisMap.put("1f468_200d_1f466", R.drawable.emoji_1f468_200d_1f466); // man-boy
        sEmojisMap.put("1f468_200d_1f466_200d_1f466", R.drawable.emoji_1f468_200d_1f466_200d_1f466); // man-boy-boy
        sEmojisMap.put("1f468_200d_1f467", R.drawable.emoji_1f468_200d_1f467); // man-girl
        sEmojisMap.put("1f468_200d_1f467_200d_1f466", R.drawable.emoji_1f468_200d_1f467_200d_1f466); // man-girl-boy
        sEmojisMap.put("1f468_200d_1f467_200d_1f467", R.drawable.emoji_1f468_200d_1f467_200d_1f467); // man-girl-girl
        sEmojisMap.put("1f469_200d_1f466", R.drawable.emoji_1f469_200d_1f466); // woman-boy
        sEmojisMap.put("1f469_200d_1f466_200d_1f466", R.drawable.emoji_1f469_200d_1f466_200d_1f466); // woman-boy-boy
        sEmojisMap.put("1f469_200d_1f467", R.drawable.emoji_1f469_200d_1f467); // woman-girl
        sEmojisMap.put("1f469_200d_1f467_200d_1f466", R.drawable.emoji_1f469_200d_1f467_200d_1f466); // woman-girl-boy
        sEmojisMap.put("1f469_200d_1f467_200d_1f467", R.drawable.emoji_1f469_200d_1f467_200d_1f467); // woman-girl-girl
        sEmojisMap.put("1f5e3_fe0f", R.drawable.emoji_1f5e3_fe0f); // speaking_head_in_silhouette
        sEmojisMap.put("1f464", R.drawable.emoji_1f464); // bust_in_silhouette
        sEmojisMap.put("1f465", R.drawable.emoji_1f465); // busts_in_silhouette
        sEmojisMap.put("1f463", R.drawable.emoji_1f463); // footprints

        //nature

        sEmojisMap.put("1f435", R.drawable.emoji_1f435); // monkey_face
        sEmojisMap.put("1f412", R.drawable.emoji_1f412); // monkey
        sEmojisMap.put("1f98d", R.drawable.emoji_1f98d); // gorilla
        sEmojisMap.put("1f9a7", R.drawable.emoji_1f9a7); // orangutan
        sEmojisMap.put("1f436", R.drawable.emoji_1f436); // dog
        sEmojisMap.put("1f415", R.drawable.emoji_1f415); // dog2
        sEmojisMap.put("1f9ae", R.drawable.emoji_1f9ae); // guide_dog
        sEmojisMap.put("1f415_200d_1f9ba", R.drawable.emoji_1f415_200d_1f9ba); // service_dog
        sEmojisMap.put("1f429", R.drawable.emoji_1f429); // poodle
        sEmojisMap.put("1f43a", R.drawable.emoji_1f43a); // wolf
        sEmojisMap.put("1f98a", R.drawable.emoji_1f98a); // fox_face
        sEmojisMap.put("1f99d", R.drawable.emoji_1f99d); // raccoon
        sEmojisMap.put("1f431", R.drawable.emoji_1f431); // cat
        sEmojisMap.put("1f408", R.drawable.emoji_1f408); // cat2
        sEmojisMap.put("1f981", R.drawable.emoji_1f981); // lion_face
        sEmojisMap.put("1f42f", R.drawable.emoji_1f42f); // tiger
        sEmojisMap.put("1f405", R.drawable.emoji_1f405); // tiger2
        sEmojisMap.put("1f406", R.drawable.emoji_1f406); // leopard
        sEmojisMap.put("1f434", R.drawable.emoji_1f434); // horse
        sEmojisMap.put("1f40e", R.drawable.emoji_1f40e); // racehorse
        sEmojisMap.put("1f984", R.drawable.emoji_1f984); // unicorn_face
        sEmojisMap.put("1f993", R.drawable.emoji_1f993); // zebra_face
        sEmojisMap.put("1f98c", R.drawable.emoji_1f98c); // deer
        sEmojisMap.put("1f42e", R.drawable.emoji_1f42e); // cow
        sEmojisMap.put("1f402", R.drawable.emoji_1f402); // ox
        sEmojisMap.put("1f403", R.drawable.emoji_1f403); // water_buffalo
        sEmojisMap.put("1f404", R.drawable.emoji_1f404); // cow2
        sEmojisMap.put("1f437", R.drawable.emoji_1f437); // pig
        sEmojisMap.put("1f416", R.drawable.emoji_1f416); // pig2
        sEmojisMap.put("1f417", R.drawable.emoji_1f417); // boar
        sEmojisMap.put("1f43d", R.drawable.emoji_1f43d); // pig_nose
        sEmojisMap.put("1f40f", R.drawable.emoji_1f40f); // ram
        sEmojisMap.put("1f411", R.drawable.emoji_1f411); // sheep
        sEmojisMap.put("1f410", R.drawable.emoji_1f410); // goat
        sEmojisMap.put("1f42a", R.drawable.emoji_1f42a); // dromedary_camel
        sEmojisMap.put("1f42b", R.drawable.emoji_1f42b); // camel
        sEmojisMap.put("1f999", R.drawable.emoji_1f999); // llama
        sEmojisMap.put("1f992", R.drawable.emoji_1f992); // giraffe_face
        sEmojisMap.put("1f418", R.drawable.emoji_1f418); // elephant
        sEmojisMap.put("1f98f", R.drawable.emoji_1f98f); // rhinoceros
        sEmojisMap.put("1f99b", R.drawable.emoji_1f99b); // hippopotamus
        sEmojisMap.put("1f42d", R.drawable.emoji_1f42d); // mouse
        sEmojisMap.put("1f401", R.drawable.emoji_1f401); // mouse2
        sEmojisMap.put("1f400", R.drawable.emoji_1f400); // rat
        sEmojisMap.put("1f439", R.drawable.emoji_1f439); // hamster
        sEmojisMap.put("1f430", R.drawable.emoji_1f430); // rabbit
        sEmojisMap.put("1f407", R.drawable.emoji_1f407); // rabbit2
        sEmojisMap.put("1f43f_fe0f", R.drawable.emoji_1f43f_fe0f); // chipmunk
        sEmojisMap.put("1f994", R.drawable.emoji_1f994); // hedgehog
        sEmojisMap.put("1f987", R.drawable.emoji_1f987); // bat
        sEmojisMap.put("1f43b", R.drawable.emoji_1f43b); // bear
        sEmojisMap.put("1f428", R.drawable.emoji_1f428); // koala
        sEmojisMap.put("1f43c", R.drawable.emoji_1f43c); // panda_face
        sEmojisMap.put("1f9a5", R.drawable.emoji_1f9a5); // sloth
        sEmojisMap.put("1f9a6", R.drawable.emoji_1f9a6); // otter
        sEmojisMap.put("1f9a8", R.drawable.emoji_1f9a8); // skunk
        sEmojisMap.put("1f998", R.drawable.emoji_1f998); // kangaroo
        sEmojisMap.put("1f9a1", R.drawable.emoji_1f9a1); // badger
        sEmojisMap.put("1f43e", R.drawable.emoji_1f43e); // feet|paw_prints
        sEmojisMap.put("1f983", R.drawable.emoji_1f983); // turkey
        sEmojisMap.put("1f414", R.drawable.emoji_1f414); // chicken
        sEmojisMap.put("1f413", R.drawable.emoji_1f413); // rooster
        sEmojisMap.put("1f423", R.drawable.emoji_1f423); // hatching_chick
        sEmojisMap.put("1f424", R.drawable.emoji_1f424); // baby_chick
        sEmojisMap.put("1f425", R.drawable.emoji_1f425); // hatched_chick
        sEmojisMap.put("1f426", R.drawable.emoji_1f426); // bird
        sEmojisMap.put("1f427", R.drawable.emoji_1f427); // penguin
        sEmojisMap.put("1f54a_fe0f", R.drawable.emoji_1f54a_fe0f); // dove_of_peace
        sEmojisMap.put("1f985", R.drawable.emoji_1f985); // eagle
        sEmojisMap.put("1f986", R.drawable.emoji_1f986); // duck
        sEmojisMap.put("1f9a2", R.drawable.emoji_1f9a2); // swan
        sEmojisMap.put("1f989", R.drawable.emoji_1f989); // owl
        sEmojisMap.put("1f9a9", R.drawable.emoji_1f9a9); // flamingo
        sEmojisMap.put("1f99a", R.drawable.emoji_1f99a); // peacock
        sEmojisMap.put("1f99c", R.drawable.emoji_1f99c); // parrot
        sEmojisMap.put("1f438", R.drawable.emoji_1f438); // frog
        sEmojisMap.put("1f40a", R.drawable.emoji_1f40a); // crocodile
        sEmojisMap.put("1f422", R.drawable.emoji_1f422); // turtle
        sEmojisMap.put("1f98e", R.drawable.emoji_1f98e); // lizard
        sEmojisMap.put("1f40d", R.drawable.emoji_1f40d); // snake
        sEmojisMap.put("1f432", R.drawable.emoji_1f432); // dragon_face
        sEmojisMap.put("1f409", R.drawable.emoji_1f409); // dragon
        sEmojisMap.put("1f995", R.drawable.emoji_1f995); // sauropod
        sEmojisMap.put("1f996", R.drawable.emoji_1f996); // t-rex
        sEmojisMap.put("1f433", R.drawable.emoji_1f433); // whale
        sEmojisMap.put("1f40b", R.drawable.emoji_1f40b); // whale2
        sEmojisMap.put("1f42c", R.drawable.emoji_1f42c); // dolphin|flipper
        sEmojisMap.put("1f41f", R.drawable.emoji_1f41f); // fish
        sEmojisMap.put("1f420", R.drawable.emoji_1f420); // tropical_fish
        sEmojisMap.put("1f421", R.drawable.emoji_1f421); // blowfish
        sEmojisMap.put("1f988", R.drawable.emoji_1f988); // shark
        sEmojisMap.put("1f419", R.drawable.emoji_1f419); // octopus
        sEmojisMap.put("1f41a", R.drawable.emoji_1f41a); // shell
        sEmojisMap.put("1f40c", R.drawable.emoji_1f40c); // snail
        sEmojisMap.put("1f98b", R.drawable.emoji_1f98b); // butterfly
        sEmojisMap.put("1f41b", R.drawable.emoji_1f41b); // bug
        sEmojisMap.put("1f41c", R.drawable.emoji_1f41c); // ant
        sEmojisMap.put("1f41d", R.drawable.emoji_1f41d); // bee|honeybee
        sEmojisMap.put("1f41e", R.drawable.emoji_1f41e); // beetle
        sEmojisMap.put("1f997", R.drawable.emoji_1f997); // cricket
        sEmojisMap.put("1f577_fe0f", R.drawable.emoji_1f577_fe0f); // spider
        sEmojisMap.put("1f578_fe0f", R.drawable.emoji_1f578_fe0f); // spider_web
        sEmojisMap.put("1f982", R.drawable.emoji_1f982); // scorpion
        sEmojisMap.put("1f99f", R.drawable.emoji_1f99f); // mosquito
        sEmojisMap.put("1f9a0", R.drawable.emoji_1f9a0); // microbe
        sEmojisMap.put("1f490", R.drawable.emoji_1f490); // bouquet
        sEmojisMap.put("1f338", R.drawable.emoji_1f338); // cherry_blossom
        sEmojisMap.put("1f4ae", R.drawable.emoji_1f4ae); // white_flower
        sEmojisMap.put("1f3f5_fe0f", R.drawable.emoji_1f3f5_fe0f); // rosette
        sEmojisMap.put("1f339", R.drawable.emoji_1f339); // rose
        sEmojisMap.put("1f940", R.drawable.emoji_1f940); // wilted_flower
        sEmojisMap.put("1f33a", R.drawable.emoji_1f33a); // hibiscus
        sEmojisMap.put("1f33b", R.drawable.emoji_1f33b); // sunflower
        sEmojisMap.put("1f33c", R.drawable.emoji_1f33c); // blossom
        sEmojisMap.put("1f337", R.drawable.emoji_1f337); // tulip
        sEmojisMap.put("1f331", R.drawable.emoji_1f331); // seedling
        sEmojisMap.put("1f332", R.drawable.emoji_1f332); // evergreen_tree
        sEmojisMap.put("1f333", R.drawable.emoji_1f333); // deciduous_tree
        sEmojisMap.put("1f334", R.drawable.emoji_1f334); // palm_tree
        sEmojisMap.put("1f335", R.drawable.emoji_1f335); // cactus
        sEmojisMap.put("1f33e", R.drawable.emoji_1f33e); // ear_of_rice
        sEmojisMap.put("1f33f", R.drawable.emoji_1f33f); // herb
        sEmojisMap.put("2618", R.drawable.emoji_2618); // shamrock
        // sEmojisMap.put("2618_fe0f", R.drawable.emoji_2618_fe0f); // shamrock
        sEmojisMap.put("1f340", R.drawable.emoji_1f340); // four_leaf_clover
        sEmojisMap.put("1f341", R.drawable.emoji_1f341); // maple_leaf
        sEmojisMap.put("1f342", R.drawable.emoji_1f342); // fallen_leaf
        sEmojisMap.put("1f343", R.drawable.emoji_1f343); // leaves

        //food_and_drink

        sEmojisMap.put("1f347", R.drawable.emoji_1f347); // grapes
        sEmojisMap.put("1f348", R.drawable.emoji_1f348); // melon
        sEmojisMap.put("1f349", R.drawable.emoji_1f349); // watermelon
        sEmojisMap.put("1f34a", R.drawable.emoji_1f34a); // tangerine
        sEmojisMap.put("1f34b", R.drawable.emoji_1f34b); // lemon
        sEmojisMap.put("1f34c", R.drawable.emoji_1f34c); // banana
        sEmojisMap.put("1f34d", R.drawable.emoji_1f34d); // pineapple
        sEmojisMap.put("1f96d", R.drawable.emoji_1f96d); // mango
        sEmojisMap.put("1f34e", R.drawable.emoji_1f34e); // apple
        sEmojisMap.put("1f34f", R.drawable.emoji_1f34f); // green_apple
        sEmojisMap.put("1f350", R.drawable.emoji_1f350); // pear
        sEmojisMap.put("1f351", R.drawable.emoji_1f351); // peach
        sEmojisMap.put("1f352", R.drawable.emoji_1f352); // cherries
        sEmojisMap.put("1f353", R.drawable.emoji_1f353); // strawberry
        sEmojisMap.put("1f95d", R.drawable.emoji_1f95d); // kiwifruit
        sEmojisMap.put("1f345", R.drawable.emoji_1f345); // tomato
        sEmojisMap.put("1f965", R.drawable.emoji_1f965); // coconut
        sEmojisMap.put("1f951", R.drawable.emoji_1f951); // avocado
        sEmojisMap.put("1f346", R.drawable.emoji_1f346); // eggplant
        sEmojisMap.put("1f954", R.drawable.emoji_1f954); // potato
        sEmojisMap.put("1f955", R.drawable.emoji_1f955); // carrot
        sEmojisMap.put("1f33d", R.drawable.emoji_1f33d); // corn
        sEmojisMap.put("1f336_fe0f", R.drawable.emoji_1f336_fe0f); // hot_pepper
        sEmojisMap.put("1f952", R.drawable.emoji_1f952); // cucumber
        sEmojisMap.put("1f96c", R.drawable.emoji_1f96c); // leafy_green
        sEmojisMap.put("1f966", R.drawable.emoji_1f966); // broccoli
        sEmojisMap.put("1f9c4", R.drawable.emoji_1f9c4); // garlic
        sEmojisMap.put("1f9c5", R.drawable.emoji_1f9c5); // onion
        sEmojisMap.put("1f344", R.drawable.emoji_1f344); // mushroom
        sEmojisMap.put("1f95c", R.drawable.emoji_1f95c); // peanuts
        sEmojisMap.put("1f330", R.drawable.emoji_1f330); // chestnut
        sEmojisMap.put("1f35e", R.drawable.emoji_1f35e); // bread
        sEmojisMap.put("1f950", R.drawable.emoji_1f950); // croissant
        sEmojisMap.put("1f956", R.drawable.emoji_1f956); // baguette_bread
        sEmojisMap.put("1f968", R.drawable.emoji_1f968); // pretzel
        sEmojisMap.put("1f96f", R.drawable.emoji_1f96f); // bagel
        sEmojisMap.put("1f95e", R.drawable.emoji_1f95e); // pancakes
        sEmojisMap.put("1f9c7", R.drawable.emoji_1f9c7); // waffle
        sEmojisMap.put("1f9c0", R.drawable.emoji_1f9c0); // cheese_wedge
        sEmojisMap.put("1f356", R.drawable.emoji_1f356); // meat_on_bone
        sEmojisMap.put("1f357", R.drawable.emoji_1f357); // poultry_leg
        sEmojisMap.put("1f969", R.drawable.emoji_1f969); // cut_of_meat
        sEmojisMap.put("1f953", R.drawable.emoji_1f953); // bacon
        sEmojisMap.put("1f354", R.drawable.emoji_1f354); // hamburger
        sEmojisMap.put("1f35f", R.drawable.emoji_1f35f); // fries
        sEmojisMap.put("1f355", R.drawable.emoji_1f355); // pizza
        sEmojisMap.put("1f32d", R.drawable.emoji_1f32d); // hotdog
        sEmojisMap.put("1f96a", R.drawable.emoji_1f96a); // sandwich
        sEmojisMap.put("1f32e", R.drawable.emoji_1f32e); // taco
        sEmojisMap.put("1f32f", R.drawable.emoji_1f32f); // burrito
        sEmojisMap.put("1f959", R.drawable.emoji_1f959); // stuffed_flatbread
        sEmojisMap.put("1f9c6", R.drawable.emoji_1f9c6); // falafel
        sEmojisMap.put("1f95a", R.drawable.emoji_1f95a); // egg
        sEmojisMap.put("1f373", R.drawable.emoji_1f373); // fried_egg|cooking
        sEmojisMap.put("1f958", R.drawable.emoji_1f958); // shallow_pan_of_food
        sEmojisMap.put("1f372", R.drawable.emoji_1f372); // stew
        sEmojisMap.put("1f963", R.drawable.emoji_1f963); // bowl_with_spoon
        sEmojisMap.put("1f957", R.drawable.emoji_1f957); // green_salad
        sEmojisMap.put("1f37f", R.drawable.emoji_1f37f); // popcorn
        sEmojisMap.put("1f9c8", R.drawable.emoji_1f9c8); // butter
        sEmojisMap.put("1f9c2", R.drawable.emoji_1f9c2); // salt
        sEmojisMap.put("1f96b", R.drawable.emoji_1f96b); // canned_food
        sEmojisMap.put("1f371", R.drawable.emoji_1f371); // bento
        sEmojisMap.put("1f358", R.drawable.emoji_1f358); // rice_cracker
        sEmojisMap.put("1f359", R.drawable.emoji_1f359); // rice_ball
        sEmojisMap.put("1f35a", R.drawable.emoji_1f35a); // rice
        sEmojisMap.put("1f35b", R.drawable.emoji_1f35b); // curry
        sEmojisMap.put("1f35c", R.drawable.emoji_1f35c); // ramen
        sEmojisMap.put("1f35d", R.drawable.emoji_1f35d); // spaghetti
        sEmojisMap.put("1f360", R.drawable.emoji_1f360); // sweet_potato
        sEmojisMap.put("1f362", R.drawable.emoji_1f362); // oden
        sEmojisMap.put("1f363", R.drawable.emoji_1f363); // sushi
        sEmojisMap.put("1f364", R.drawable.emoji_1f364); // fried_shrimp
        sEmojisMap.put("1f365", R.drawable.emoji_1f365); // fish_cake
        sEmojisMap.put("1f96e", R.drawable.emoji_1f96e); // moon_cake
        sEmojisMap.put("1f361", R.drawable.emoji_1f361); // dango
        sEmojisMap.put("1f95f", R.drawable.emoji_1f95f); // dumpling
        sEmojisMap.put("1f960", R.drawable.emoji_1f960); // fortune_cookie
        sEmojisMap.put("1f961", R.drawable.emoji_1f961); // takeout_box
        sEmojisMap.put("1f980", R.drawable.emoji_1f980); // crab
        sEmojisMap.put("1f99e", R.drawable.emoji_1f99e); // lobster
        sEmojisMap.put("1f990", R.drawable.emoji_1f990); // shrimp
        sEmojisMap.put("1f991", R.drawable.emoji_1f991); // squid
        sEmojisMap.put("1f9aa", R.drawable.emoji_1f9aa); // oyster
        sEmojisMap.put("1f366", R.drawable.emoji_1f366); // icecream
        sEmojisMap.put("1f367", R.drawable.emoji_1f367); // shaved_ice
        sEmojisMap.put("1f368", R.drawable.emoji_1f368); // ice_cream
        sEmojisMap.put("1f369", R.drawable.emoji_1f369); // doughnut
        sEmojisMap.put("1f36a", R.drawable.emoji_1f36a); // cookie
        sEmojisMap.put("1f382", R.drawable.emoji_1f382); // birthday
        sEmojisMap.put("1f370", R.drawable.emoji_1f370); // cake
        sEmojisMap.put("1f9c1", R.drawable.emoji_1f9c1); // cupcake
        sEmojisMap.put("1f967", R.drawable.emoji_1f967); // pie
        sEmojisMap.put("1f36b", R.drawable.emoji_1f36b); // chocolate_bar
        sEmojisMap.put("1f36c", R.drawable.emoji_1f36c); // candy
        sEmojisMap.put("1f36d", R.drawable.emoji_1f36d); // lollipop
        sEmojisMap.put("1f36e", R.drawable.emoji_1f36e); // custard
        sEmojisMap.put("1f36f", R.drawable.emoji_1f36f); // honey_pot
        sEmojisMap.put("1f37c", R.drawable.emoji_1f37c); // baby_bottle
        sEmojisMap.put("1f95b", R.drawable.emoji_1f95b); // glass_of_milk
        sEmojisMap.put("2615", R.drawable.emoji_2615); // coffee
        sEmojisMap.put("1f375", R.drawable.emoji_1f375); // tea
        sEmojisMap.put("1f376", R.drawable.emoji_1f376); // sake
        sEmojisMap.put("1f37e", R.drawable.emoji_1f37e); // champagne
        sEmojisMap.put("1f377", R.drawable.emoji_1f377); // wine_glass
        sEmojisMap.put("1f378", R.drawable.emoji_1f378); // cocktail
        sEmojisMap.put("1f379", R.drawable.emoji_1f379); // tropical_drink
        sEmojisMap.put("1f37a", R.drawable.emoji_1f37a); // beer
        sEmojisMap.put("1f37b", R.drawable.emoji_1f37b); // beers
        sEmojisMap.put("1f942", R.drawable.emoji_1f942); // clinking_glasses
        sEmojisMap.put("1f943", R.drawable.emoji_1f943); // tumbler_glass
        sEmojisMap.put("1f964", R.drawable.emoji_1f964); // cup_with_straw
        sEmojisMap.put("1f9c3", R.drawable.emoji_1f9c3); // beverage_box
        sEmojisMap.put("1f9c9", R.drawable.emoji_1f9c9); // mate_drink
        sEmojisMap.put("1f9ca", R.drawable.emoji_1f9ca); // ice_cube
        sEmojisMap.put("1f962", R.drawable.emoji_1f962); // chopsticks
        sEmojisMap.put("1f37d_fe0f", R.drawable.emoji_1f37d_fe0f); // knife_fork_plate
        sEmojisMap.put("1f374", R.drawable.emoji_1f374);  // fork_and_knife
        sEmojisMap.put("1f944", R.drawable.emoji_1f944);  // spoon
        sEmojisMap.put("1f52a", R.drawable.emoji_1f52a);  // hocho|knife
        sEmojisMap.put("1f3fa", R.drawable.emoji_1f3fa);  // amphora

        //places

        sEmojisMap.put("1f30d", R.drawable.emoji_1f30d); // earth_africa
        sEmojisMap.put("1f30e", R.drawable.emoji_1f30e); // earth_americas
        sEmojisMap.put("1f30f", R.drawable.emoji_1f30f); // earth_asia
        sEmojisMap.put("1f310", R.drawable.emoji_1f310); // globe_with_meridians
        sEmojisMap.put("1f5fa_fe0f", R.drawable.emoji_1f5fa_fe0f); // world_map
        sEmojisMap.put("1f5fe", R.drawable.emoji_1f5fe); // japan
        sEmojisMap.put("1f9ed", R.drawable.emoji_1f9ed); // compass
        sEmojisMap.put("1f3d4_fe0f", R.drawable.emoji_1f3d4_fe0f); // snow_capped_mountain
        sEmojisMap.put("26f0", R.drawable.emoji_26f0); // mountain
        // sEmojisMap.put("26f0_fe0f", R.drawable.emoji_26f0_fe0f); // mountain
        sEmojisMap.put("1f30b", R.drawable.emoji_1f30b); // volcano
        sEmojisMap.put("1f5fb", R.drawable.emoji_1f5fb); // mount_fuji
        sEmojisMap.put("1f3d5_fe0f", R.drawable.emoji_1f3d5_fe0f); // camping
        sEmojisMap.put("1f3d6_fe0f", R.drawable.emoji_1f3d6_fe0f); // beach_with_umbrella
        sEmojisMap.put("1f3dc_fe0f", R.drawable.emoji_1f3dc_fe0f); // desert
        sEmojisMap.put("1f3dd_fe0f", R.drawable.emoji_1f3dd_fe0f); // desert_island
        sEmojisMap.put("1f3de_fe0f", R.drawable.emoji_1f3de_fe0f); // national_park
        sEmojisMap.put("1f3df_fe0f", R.drawable.emoji_1f3df_fe0f); // stadium
        sEmojisMap.put("1f3db_fe0f", R.drawable.emoji_1f3db_fe0f); // classical_building
        sEmojisMap.put("1f3d7_fe0f", R.drawable.emoji_1f3d7_fe0f); // building_construction
        sEmojisMap.put("1f9f1", R.drawable.emoji_1f9f1); // bricks
        sEmojisMap.put("1f3d8_fe0f", R.drawable.emoji_1f3d8_fe0f); // house_buildings
        sEmojisMap.put("1f3da_fe0f", R.drawable.emoji_1f3da_fe0f); // derelict_house_building
        sEmojisMap.put("1f3e0", R.drawable.emoji_1f3e0); // house
        sEmojisMap.put("1f3e1", R.drawable.emoji_1f3e1); // house_with_garden
        sEmojisMap.put("1f3e2", R.drawable.emoji_1f3e2); // office
        sEmojisMap.put("1f3e3", R.drawable.emoji_1f3e3); // post_office
        sEmojisMap.put("1f3e4", R.drawable.emoji_1f3e4); // european_post_office
        sEmojisMap.put("1f3e5", R.drawable.emoji_1f3e5); // hospital
        sEmojisMap.put("1f3e6", R.drawable.emoji_1f3e6); // bank
        sEmojisMap.put("1f3e8", R.drawable.emoji_1f3e8); // hotel
        sEmojisMap.put("1f3e9", R.drawable.emoji_1f3e9); // love_hotel
        sEmojisMap.put("1f3ea", R.drawable.emoji_1f3ea); // convenience_store
        sEmojisMap.put("1f3eb", R.drawable.emoji_1f3eb); // school
        sEmojisMap.put("1f3ec", R.drawable.emoji_1f3ec); // department_store
        sEmojisMap.put("1f3ed", R.drawable.emoji_1f3ed); // factory
        sEmojisMap.put("1f3ef", R.drawable.emoji_1f3ef); // japanese_castle
        sEmojisMap.put("1f3f0", R.drawable.emoji_1f3f0); // european_castle
        sEmojisMap.put("1f492", R.drawable.emoji_1f492); // wedding
        sEmojisMap.put("1f5fc", R.drawable.emoji_1f5fc); // tokyo_tower
        sEmojisMap.put("1f5fd", R.drawable.emoji_1f5fd); // statue_of_liberty
        sEmojisMap.put("26ea", R.drawable.emoji_26ea); // church
        sEmojisMap.put("1f54c", R.drawable.emoji_1f54c); // mosque
        sEmojisMap.put("1f6d5", R.drawable.emoji_1f6d5); // hindu_temple
        sEmojisMap.put("1f54d", R.drawable.emoji_1f54d); // synagogue
        sEmojisMap.put("26e9", R.drawable.emoji_26e9); // shinto_shrine
        // sEmojisMap.put("26e9_fe0f", R.drawable.emoji_26e9_fe0f); // shinto_shrine
        sEmojisMap.put("1f54b", R.drawable.emoji_1f54b); // kaaba
        sEmojisMap.put("26f2", R.drawable.emoji_26f2); // fountain
        sEmojisMap.put("26fa", R.drawable.emoji_26fa); // tent
        sEmojisMap.put("1f301", R.drawable.emoji_1f301); // foggy
        sEmojisMap.put("1f303", R.drawable.emoji_1f303); // night_with_stars
        sEmojisMap.put("1f3d9_fe0f", R.drawable.emoji_1f3d9_fe0f); // cityscape
        sEmojisMap.put("1f304", R.drawable.emoji_1f304); // sunrise_over_mountains
        sEmojisMap.put("1f305", R.drawable.emoji_1f305); // sunrise
        sEmojisMap.put("1f306", R.drawable.emoji_1f306); // city_sunset
        sEmojisMap.put("1f307", R.drawable.emoji_1f307); // city_sunrise
        sEmojisMap.put("1f309", R.drawable.emoji_1f309); // bridge_at_night
        sEmojisMap.put("2668", R.drawable.emoji_2668); // hotsprings
        // sEmojisMap.put("2668_fe0f", R.drawable.emoji_2668_fe0f); // hotsprings
        sEmojisMap.put("1f3a0", R.drawable.emoji_1f3a0); // carousel_horse
        sEmojisMap.put("1f3a1", R.drawable.emoji_1f3a1); // ferris_wheel
        sEmojisMap.put("1f3a2", R.drawable.emoji_1f3a2); // roller_coaster
        sEmojisMap.put("1f488", R.drawable.emoji_1f488); // barber
        sEmojisMap.put("1f3aa", R.drawable.emoji_1f3aa); // circus_tent
        sEmojisMap.put("1f682", R.drawable.emoji_1f682); // steam_locomotive
        sEmojisMap.put("1f683", R.drawable.emoji_1f683); // railway_car
        sEmojisMap.put("1f684", R.drawable.emoji_1f684); // bullettrain_side
        sEmojisMap.put("1f685", R.drawable.emoji_1f685); // bullettrain_front
        sEmojisMap.put("1f686", R.drawable.emoji_1f686); // train2
        sEmojisMap.put("1f687", R.drawable.emoji_1f687); // metro
        sEmojisMap.put("1f688", R.drawable.emoji_1f688); // light_rail
        sEmojisMap.put("1f689", R.drawable.emoji_1f689); // station
        sEmojisMap.put("1f68a", R.drawable.emoji_1f68a); // tram
        sEmojisMap.put("1f69d", R.drawable.emoji_1f69d); // monorail
        sEmojisMap.put("1f69e", R.drawable.emoji_1f69e); // mountain_railway
        sEmojisMap.put("1f68b", R.drawable.emoji_1f68b); // train
        sEmojisMap.put("1f68c", R.drawable.emoji_1f68c); // bus
        sEmojisMap.put("1f68d", R.drawable.emoji_1f68d); // oncoming_bus
        sEmojisMap.put("1f68e", R.drawable.emoji_1f68e); // trolleybus
        sEmojisMap.put("1f690", R.drawable.emoji_1f690); // minibus
        sEmojisMap.put("1f691", R.drawable.emoji_1f691); // ambulance
        sEmojisMap.put("1f692", R.drawable.emoji_1f692); // fire_engine
        sEmojisMap.put("1f693", R.drawable.emoji_1f693); // police_car
        sEmojisMap.put("1f694", R.drawable.emoji_1f694); // oncoming_police_car
        sEmojisMap.put("1f695", R.drawable.emoji_1f695); // taxi
        sEmojisMap.put("1f696", R.drawable.emoji_1f696); // oncoming_taxi
        sEmojisMap.put("1f697", R.drawable.emoji_1f697); // car|red_car
        sEmojisMap.put("1f698", R.drawable.emoji_1f698); // oncoming_automobile
        sEmojisMap.put("1f699", R.drawable.emoji_1f699); // blue_car
        sEmojisMap.put("1f69a", R.drawable.emoji_1f69a); // truck
        sEmojisMap.put("1f69b", R.drawable.emoji_1f69b); // articulated_lorry
        sEmojisMap.put("1f69c", R.drawable.emoji_1f69c); // tractor
        sEmojisMap.put("1f3ce_fe0f", R.drawable.emoji_1f3ce_fe0f); // racing_car
        sEmojisMap.put("1f3cd_fe0f", R.drawable.emoji_1f3cd_fe0f); // racing_motorcycle
        sEmojisMap.put("1f6f5", R.drawable.emoji_1f6f5); // motor_scooter
        sEmojisMap.put("1f9bd", R.drawable.emoji_1f9bd); // manual_wheelchair
        sEmojisMap.put("1f9bc", R.drawable.emoji_1f9bc); // motorized_wheelchair
        sEmojisMap.put("1f6fa", R.drawable.emoji_1f6fa); // auto_rickshaw
        sEmojisMap.put("1f6b2", R.drawable.emoji_1f6b2); // bike
        sEmojisMap.put("1f6f4", R.drawable.emoji_1f6f4); // scooter
        sEmojisMap.put("1f6f9", R.drawable.emoji_1f6f9); // skateboard
        sEmojisMap.put("1f68f", R.drawable.emoji_1f68f); // busstop
        sEmojisMap.put("1f6e3_fe0f", R.drawable.emoji_1f6e3_fe0f); // motorway
        sEmojisMap.put("1f6e4_fe0f", R.drawable.emoji_1f6e4_fe0f); // railway_track
        sEmojisMap.put("1f6e2_fe0f", R.drawable.emoji_1f6e2_fe0f); // oil_drum
        sEmojisMap.put("26fd", R.drawable.emoji_26fd); // fuelpump
        sEmojisMap.put("1f6a8", R.drawable.emoji_1f6a8); // rotating_light
        sEmojisMap.put("1f6a5", R.drawable.emoji_1f6a5); // traffic_light
        sEmojisMap.put("1f6a6", R.drawable.emoji_1f6a6); // vertical_traffic_light
        sEmojisMap.put("1f6d1", R.drawable.emoji_1f6d1); // octagonal_sign
        sEmojisMap.put("1f6a7", R.drawable.emoji_1f6a7); // construction
        sEmojisMap.put("2693", R.drawable.emoji_2693); // anchor
        sEmojisMap.put("26f5", R.drawable.emoji_26f5); // boat|sailboat
        sEmojisMap.put("1f6f6", R.drawable.emoji_1f6f6); // canoe
        sEmojisMap.put("1f6a4", R.drawable.emoji_1f6a4); // speedboat
        sEmojisMap.put("1f6f3_fe0f", R.drawable.emoji_1f6f3_fe0f); // passenger_ship
        sEmojisMap.put("26f4", R.drawable.emoji_26f4); // ferry
        // sEmojisMap.put("26f4_fe0f", R.drawable.emoji_26f4_fe0f); // ferry
        sEmojisMap.put("1f6e5_fe0f", R.drawable.emoji_1f6e5_fe0f); // motor_boat
        sEmojisMap.put("1f6a2", R.drawable.emoji_1f6a2); // ship
        sEmojisMap.put("2708", R.drawable.emoji_2708); // airplane
        // sEmojisMap.put("2708_fe0f", R.drawable.emoji_2708_fe0f); // airplane
        sEmojisMap.put("1f6e9_fe0f", R.drawable.emoji_1f6e9_fe0f); // small_airplane
        sEmojisMap.put("1f6eb", R.drawable.emoji_1f6eb); // airplane_departure
        sEmojisMap.put("1f6ec", R.drawable.emoji_1f6ec); // airplane_arriving
        sEmojisMap.put("1fa82", R.drawable.emoji_1fa82); // parachute
        sEmojisMap.put("1f4ba", R.drawable.emoji_1f4ba); // seat
        sEmojisMap.put("1f681", R.drawable.emoji_1f681); // helicopter
        sEmojisMap.put("1f69f", R.drawable.emoji_1f69f); // suspension_railway
        sEmojisMap.put("1f6a0", R.drawable.emoji_1f6a0); // mountain_cableway
        sEmojisMap.put("1f6a1", R.drawable.emoji_1f6a1); // aerial_tramway
        sEmojisMap.put("1f6f0_fe0f", R.drawable.emoji_1f6f0_fe0f); // satellite
        sEmojisMap.put("1f680", R.drawable.emoji_1f680); // rocket
        sEmojisMap.put("1f6f8", R.drawable.emoji_1f6f8); // flying_saucer
        sEmojisMap.put("1f6ce_fe0f", R.drawable.emoji_1f6ce_fe0f); // bellhop_bell
        sEmojisMap.put("1f9f3", R.drawable.emoji_1f9f3); // luggage
        sEmojisMap.put("231b", R.drawable.emoji_231b); // hourglass
        sEmojisMap.put("23f3", R.drawable.emoji_23f3); // hourglass_flowing_sand
        sEmojisMap.put("231a", R.drawable.emoji_231a); // watch
        sEmojisMap.put("23f0", R.drawable.emoji_23f0); // alarm_clock
        sEmojisMap.put("23f1", R.drawable.emoji_23f1); // stopwatch
        // sEmojisMap.put("23f1_fe0f", R.drawable.emoji_23f1_fe0f); // stopwatch
        sEmojisMap.put("23f2", R.drawable.emoji_23f2); // timer_clock
        // sEmojisMap.put("23ff", R.drawable.emoji_23ff_fe0f); // timer_clock
        // sEmojisMap.put("23f2_fe0f", R.drawable.emoji_23f2_fe0f); // timer_clock
        sEmojisMap.put("1f570_fe0f", R.drawable.emoji_1f570_fe0f); // mantelpiece_clock
        sEmojisMap.put("1f55b", R.drawable.emoji_1f55b); // clock12
        sEmojisMap.put("1f567", R.drawable.emoji_1f567); // clock1230
        sEmojisMap.put("1f550", R.drawable.emoji_1f550); // clock1
        sEmojisMap.put("1f55c", R.drawable.emoji_1f55c); // clock130
        sEmojisMap.put("1f551", R.drawable.emoji_1f551); // clock2
        sEmojisMap.put("1f55d", R.drawable.emoji_1f55d); // clock230
        sEmojisMap.put("1f552", R.drawable.emoji_1f552); // clock3
        sEmojisMap.put("1f55e", R.drawable.emoji_1f55e); // clock330
        sEmojisMap.put("1f553", R.drawable.emoji_1f553); // clock4
        sEmojisMap.put("1f55f", R.drawable.emoji_1f55f); // clock430
        sEmojisMap.put("1f554", R.drawable.emoji_1f554); // clock5
        sEmojisMap.put("1f560", R.drawable.emoji_1f560); // clock530
        sEmojisMap.put("1f555", R.drawable.emoji_1f555); // clock6
        sEmojisMap.put("1f561", R.drawable.emoji_1f561); // clock630
        sEmojisMap.put("1f556", R.drawable.emoji_1f556); // clock7
        sEmojisMap.put("1f562", R.drawable.emoji_1f562); // clock730
        sEmojisMap.put("1f557", R.drawable.emoji_1f557); // clock8
        sEmojisMap.put("1f563", R.drawable.emoji_1f563); // clock830
        sEmojisMap.put("1f558", R.drawable.emoji_1f558); // clock9
        sEmojisMap.put("1f564", R.drawable.emoji_1f564); // clock930
        sEmojisMap.put("1f559", R.drawable.emoji_1f559); // clock10
        sEmojisMap.put("1f565", R.drawable.emoji_1f565); // clock1030
        sEmojisMap.put("1f55a", R.drawable.emoji_1f55a); // clock11
        sEmojisMap.put("1f566", R.drawable.emoji_1f566); // clock1130
        sEmojisMap.put("1f311", R.drawable.emoji_1f311); // new_moon
        sEmojisMap.put("1f312", R.drawable.emoji_1f312); // waxing_crescent_moon
        sEmojisMap.put("1f313", R.drawable.emoji_1f313); // first_quarter_moon
        sEmojisMap.put("1f314", R.drawable.emoji_1f314); // moon|waxing_gibbous_moon
        sEmojisMap.put("1f315", R.drawable.emoji_1f315); // full_moon
        sEmojisMap.put("1f316", R.drawable.emoji_1f316); // waning_gibbous_moon
        sEmojisMap.put("1f317", R.drawable.emoji_1f317); // last_quarter_moon
        sEmojisMap.put("1f318", R.drawable.emoji_1f318); // waning_crescent_moon
        sEmojisMap.put("1f319", R.drawable.emoji_1f319); // crescent_moon
        sEmojisMap.put("1f31a", R.drawable.emoji_1f31a); // new_moon_with_face
        sEmojisMap.put("1f31b", R.drawable.emoji_1f31b); // first_quarter_moon_with_face
        sEmojisMap.put("1f31c", R.drawable.emoji_1f31c); // last_quarter_moon_with_face
        sEmojisMap.put("1f321_fe0f", R.drawable.emoji_1f321_fe0f); // thermometer
        sEmojisMap.put("2600", R.drawable.emoji_2600); // sunny
        // sEmojisMap.put("2600_fe0f", R.drawable.emoji_2600_fe0f); // sunny
        sEmojisMap.put("1f31d", R.drawable.emoji_1f31d); // full_moon_with_face
        sEmojisMap.put("1f31e", R.drawable.emoji_1f31e); // sun_with_face
        sEmojisMap.put("1fa90", R.drawable.emoji_1fa90); // ringed_planet
        sEmojisMap.put("2b50", R.drawable.emoji_2b50); // star
        // sEmojisMap.put("2b50_fe0f", R.drawable.emoji_2b50_fe0f); // }, new String[0], 55, 42, true
        sEmojisMap.put("1f31f", R.drawable.emoji_1f31f); // star2
        sEmojisMap.put("1f320", R.drawable.emoji_1f320); // stars
        sEmojisMap.put("1f30c", R.drawable.emoji_1f30c); // milky_way
        sEmojisMap.put("2601", R.drawable.emoji_2601); // cloud
        // sEmojisMap.put("2601_fe0f", R.drawable.emoji_2601_fe0f); // cloud
        sEmojisMap.put("26c5", R.drawable.emoji_26c5); // partly_sunny
        sEmojisMap.put("26c8", R.drawable.emoji_26c8); // thunder_cloud_and_rain
        // sEmojisMap.put("26c8_fe0f", R.drawable.emoji_26c8_fe0f); // thunder_cloud_and_rain
        sEmojisMap.put("1f324_fe0f", R.drawable.emoji_1f324_fe0f); // mostly_sunny|sun_small_cloud
        sEmojisMap.put("1f325_fe0f", R.drawable.emoji_1f325_fe0f); // barely_sunny|sun_behind_cloud
        sEmojisMap.put("1f326_fe0f", R.drawable.emoji_1f326_fe0f); // partly_sunny_rain|sun_behind_rain_cloud
        sEmojisMap.put("1f327_fe0f", R.drawable.emoji_1f327_fe0f); // rain_cloud
        sEmojisMap.put("1f328_fe0f", R.drawable.emoji_1f328_fe0f); // snow_cloud
        sEmojisMap.put("1f329_fe0f", R.drawable.emoji_1f329_fe0f); // lightning|lightning_cloud
        sEmojisMap.put("1f32a_fe0f", R.drawable.emoji_1f32a_fe0f); // tornado|tornado_cloud
        sEmojisMap.put("1f32b_fe0f", R.drawable.emoji_1f32b_fe0f); // fog
        sEmojisMap.put("1f32c_fe0f", R.drawable.emoji_1f32c_fe0f); // wind_blowing_face
        sEmojisMap.put("1f300", R.drawable.emoji_1f300); // cyclone
        sEmojisMap.put("1f308", R.drawable.emoji_1f308); // rainbow
        sEmojisMap.put("1f302", R.drawable.emoji_1f302); // closed_umbrella
        sEmojisMap.put("2602", R.drawable.emoji_2602); // umbrella
        // sEmojisMap.put("2602_fe0f", R.drawable.emoji_2602_fe0f); // umbrella
        sEmojisMap.put("2614", R.drawable.emoji_2614); // umbrella_with_rain_drops
        sEmojisMap.put("26f1", R.drawable.emoji_26f1); // umbrella_on_ground
        // sEmojisMap.put("26f1_fe0f", R.drawable.emoji_26f1_fe0f); // umbrella_on_ground
        sEmojisMap.put("26a1", R.drawable.emoji_26a1); // zap
        sEmojisMap.put("2744", R.drawable.emoji_2744); // snowflake
        // sEmojisMap.put("2744_fe0f", R.drawable.emoji_2744_fe0f); // snowflake
        sEmojisMap.put("2603", R.drawable.emoji_2603); // snowman
        // sEmojisMap.put("2603_fe0f", R.drawable.emoji_2603_fe0f); // snowman
        sEmojisMap.put("26c4", R.drawable.emoji_26c4); // snowman_without_snow
        sEmojisMap.put("2604", R.drawable.emoji_2604); // comet
        // sEmojisMap.put("2604_fe0f", R.drawable.emoji_2604_fe0f); // comet
        sEmojisMap.put("1f525", R.drawable.emoji_1f525); // fire
        sEmojisMap.put("1f4a7", R.drawable.emoji_1f4a7); // droplet
        sEmojisMap.put("1f30a", R.drawable.emoji_1f30a); // ocean

        //activities

        sEmojisMap.put("1f383", R.drawable.emoji_1f383); // jack_o_lantern
        sEmojisMap.put("1f384", R.drawable.emoji_1f384); // christmas_tree
        sEmojisMap.put("1f386", R.drawable.emoji_1f386); // fireworks
        sEmojisMap.put("1f387", R.drawable.emoji_1f387); // sparkler
        sEmojisMap.put("1f9e8", R.drawable.emoji_1f9e8); // firecracker
        sEmojisMap.put("2728", R.drawable.emoji_2728); // sparkles
        sEmojisMap.put("1f388", R.drawable.emoji_1f388); // balloon
        sEmojisMap.put("1f389", R.drawable.emoji_1f389); // tada
        sEmojisMap.put("1f38a", R.drawable.emoji_1f38a); // confetti_ball
        sEmojisMap.put("1f38b", R.drawable.emoji_1f38b); // tanabata_tree
        sEmojisMap.put("1f38d", R.drawable.emoji_1f38d); // bamboo
        sEmojisMap.put("1f38e", R.drawable.emoji_1f38e); // dolls
        sEmojisMap.put("1f38f", R.drawable.emoji_1f38f); // flags
        sEmojisMap.put("1f390", R.drawable.emoji_1f390); // wind_chime
        sEmojisMap.put("1f391", R.drawable.emoji_1f391); // rice_scene
        sEmojisMap.put("1f9e7", R.drawable.emoji_1f9e7); // red_envelope
        sEmojisMap.put("1f380", R.drawable.emoji_1f380); // ribbon
        sEmojisMap.put("1f381", R.drawable.emoji_1f381); // gift
        sEmojisMap.put("1f397_fe0f", R.drawable.emoji_1f397_fe0f); // reminder_ribbon
        sEmojisMap.put("1f39f_fe0f", R.drawable.emoji_1f39f_fe0f); // admission_tickets
        sEmojisMap.put("1f3ab", R.drawable.emoji_1f3ab); // ticket
        sEmojisMap.put("1f396_fe0f", R.drawable.emoji_1f396_fe0f); // medal
        sEmojisMap.put("1f3c6", R.drawable.emoji_1f3c6); // trophy
        sEmojisMap.put("1f3c5", R.drawable.emoji_1f3c5); // sports_medal
        sEmojisMap.put("1f947", R.drawable.emoji_1f947); // first_place_medal
        sEmojisMap.put("1f948", R.drawable.emoji_1f948); // second_place_medal
        sEmojisMap.put("1f949", R.drawable.emoji_1f949); // third_place_medal
        sEmojisMap.put("26bd", R.drawable.emoji_26bd); // soccer
        sEmojisMap.put("26be", R.drawable.emoji_26be); // baseball
        sEmojisMap.put("1f94e", R.drawable.emoji_1f94e); // softball
        sEmojisMap.put("1f3c0", R.drawable.emoji_1f3c0); // basketball
        sEmojisMap.put("1f3d0", R.drawable.emoji_1f3d0); // volleyball
        sEmojisMap.put("1f3c8", R.drawable.emoji_1f3c8); // football
        sEmojisMap.put("1f3c9", R.drawable.emoji_1f3c9); // rugby_football
        sEmojisMap.put("1f3be", R.drawable.emoji_1f3be); // tennis
        sEmojisMap.put("1f94f", R.drawable.emoji_1f94f); // flying_disc
        sEmojisMap.put("1f3b3", R.drawable.emoji_1f3b3); // bowling
        sEmojisMap.put("1f3cf", R.drawable.emoji_1f3cf); // cricket_bat_and_ball
        sEmojisMap.put("1f3d1", R.drawable.emoji_1f3d1); // field_hockey_stick_and_ball
        sEmojisMap.put("1f3d2", R.drawable.emoji_1f3d2); // ice_hockey_stick_and_puck
        sEmojisMap.put("1f94d", R.drawable.emoji_1f94d); // lacrosse
        sEmojisMap.put("1f3d3", R.drawable.emoji_1f3d3); // table_tennis_paddle_and_ball
        sEmojisMap.put("1f3f8", R.drawable.emoji_1f3f8); // badminton_racquet_and_shuttlecock
        sEmojisMap.put("1f94a", R.drawable.emoji_1f94a); // boxing_glove
        sEmojisMap.put("1f94b", R.drawable.emoji_1f94b); // martial_arts_uniform
        sEmojisMap.put("1f945", R.drawable.emoji_1f945); // goal_net
        sEmojisMap.put("26f3", R.drawable.emoji_26f3); // golf
        sEmojisMap.put("26f8", R.drawable.emoji_26f8); // ice_skate
        // sEmojisMap.put("26f8_fe0f", R.drawable.emoji_26f8_fe0f); // ice_skate
        sEmojisMap.put("1f3a3", R.drawable.emoji_1f3a3); // fishing_pole_and_fish
        sEmojisMap.put("1f93f", R.drawable.emoji_1f93f); // diving_mask
        sEmojisMap.put("1f3bd", R.drawable.emoji_1f3bd); // running_shirt_with_sash
        sEmojisMap.put("1f3bf", R.drawable.emoji_1f3bf); // ski
        sEmojisMap.put("1f6f7", R.drawable.emoji_1f6f7); // sled
        sEmojisMap.put("1f94c", R.drawable.emoji_1f94c); // curling_stone
        sEmojisMap.put("1f3af", R.drawable.emoji_1f3af); // dart
        sEmojisMap.put("1fa80", R.drawable.emoji_1fa80); // yo-yo
        sEmojisMap.put("1fa81", R.drawable.emoji_1fa81); // kite
        sEmojisMap.put("1f3b1", R.drawable.emoji_1f3b1); // 8ball
        sEmojisMap.put("1f52e", R.drawable.emoji_1f52e); // crystal_ball
        sEmojisMap.put("1f9ff", R.drawable.emoji_1f9ff); // nazar_amulet
        sEmojisMap.put("1f3ae", R.drawable.emoji_1f3ae); // video_game
        sEmojisMap.put("1f579_fe0f", R.drawable.emoji_1f579_fe0f); // joystick
        sEmojisMap.put("1f3b0", R.drawable.emoji_1f3b0); // slot_machine
        sEmojisMap.put("1f3b2", R.drawable.emoji_1f3b2); // game_die
        sEmojisMap.put("1f9e9", R.drawable.emoji_1f9e9); // jigsaw
        sEmojisMap.put("1f9f8", R.drawable.emoji_1f9f8); // teddy_bear
        sEmojisMap.put("2660", R.drawable.emoji_2660); // spades
        // sEmojisMap.put("2660_fe0f", R.drawable.emoji_2660_fe0f); // spades
        sEmojisMap.put("2665", R.drawable.emoji_2665); // hearts
        // sEmojisMap.put("2665_fe0f", R.drawable.emoji_2665_fe0f); // hearts
        sEmojisMap.put("2666", R.drawable.emoji_2666); // diamonds
        // sEmojisMap.put("2666_fe0f", R.drawable.emoji_2666_fe0f); // diamonds
        sEmojisMap.put("2663", R.drawable.emoji_2663); // clubs
        // sEmojisMap.put("2663_fe0f", R.drawable.emoji_2663_fe0f); // clubs
        sEmojisMap.put("265f", R.drawable.emoji_265f); // chess_pawn
        // sEmojisMap.put("265f_fe0f", R.drawable.emoji_265f_fe0f); // chess_pawn
        sEmojisMap.put("1f0cf", R.drawable.emoji_1f0cf); // black_joker
        sEmojisMap.put("1f004", R.drawable.emoji_1f004); // mahjong
        sEmojisMap.put("1f3b4", R.drawable.emoji_1f3b4); // flower_playing_cards
        sEmojisMap.put("1f3ad", R.drawable.emoji_1f3ad); // performing_arts
        sEmojisMap.put("1f5bc_fe0f", R.drawable.emoji_1f5bc_fe0f); // frame_with_picture
        sEmojisMap.put("1f3a8", R.drawable.emoji_1f3a8); // art
        sEmojisMap.put("1f9f5", R.drawable.emoji_1f9f5); // thread
        sEmojisMap.put("1f9f6", R.drawable.emoji_1f9f6); // yarn

        //objects

        sEmojisMap.put("1f453", R.drawable.emoji_1f453); //  eyeglasses
        sEmojisMap.put("1f576_fe0f", R.drawable.emoji_1f576_fe0f); // dark_sunglasses
        sEmojisMap.put("1f97d", R.drawable.emoji_1f97d); //  goggles
        sEmojisMap.put("1f97c", R.drawable.emoji_1f97c); //  lab_coat
        sEmojisMap.put("1f9ba", R.drawable.emoji_1f9ba); //  safety_vest
        sEmojisMap.put("1f454", R.drawable.emoji_1f454); //  necktie
        sEmojisMap.put("1f455", R.drawable.emoji_1f455); //  shirt|tshirt
        sEmojisMap.put("1f456", R.drawable.emoji_1f456); //  jeans
        sEmojisMap.put("1f9e3", R.drawable.emoji_1f9e3); //  scarf
        sEmojisMap.put("1f9e4", R.drawable.emoji_1f9e4); //  gloves
        sEmojisMap.put("1f9e5", R.drawable.emoji_1f9e5); //  coat
        sEmojisMap.put("1f9e6", R.drawable.emoji_1f9e6); //  socks
        sEmojisMap.put("1f457", R.drawable.emoji_1f457); //  dress
        sEmojisMap.put("1f458", R.drawable.emoji_1f458); //  kimono
        sEmojisMap.put("1f97b", R.drawable.emoji_1f97b); //  sari
        sEmojisMap.put("1fa71", R.drawable.emoji_1fa71); //  one-piece_swimsuit
        sEmojisMap.put("1fa72", R.drawable.emoji_1fa72); //  briefs
        sEmojisMap.put("1fa73", R.drawable.emoji_1fa73); //  shorts
        sEmojisMap.put("1f459", R.drawable.emoji_1f459); //  bikini
        sEmojisMap.put("1f45a", R.drawable.emoji_1f45a); //  womans_clothes
        sEmojisMap.put("1f45b", R.drawable.emoji_1f45b); //  purse
        sEmojisMap.put("1f45c", R.drawable.emoji_1f45c); //  handbag
        sEmojisMap.put("1f45d", R.drawable.emoji_1f45d); //  pouch
        sEmojisMap.put("1f6cd_fe0f", R.drawable.emoji_1f6cd_fe0f); // shopping_bags
        sEmojisMap.put("1f392", R.drawable.emoji_1f392); //  school_satchel
        sEmojisMap.put("1f45e", R.drawable.emoji_1f45e); //  mans_shoe|shoe
        sEmojisMap.put("1f45f", R.drawable.emoji_1f45f); //  athletic_shoe
        sEmojisMap.put("1f97e", R.drawable.emoji_1f97e); //  hiking_boot
        sEmojisMap.put("1f97f", R.drawable.emoji_1f97f); //  womans_flat_shoe
        sEmojisMap.put("1f460", R.drawable.emoji_1f460); //  high_heel
        sEmojisMap.put("1f461", R.drawable.emoji_1f461); //  sandal
        sEmojisMap.put("1fa70", R.drawable.emoji_1fa70); //  ballet_shoes
        sEmojisMap.put("1f462", R.drawable.emoji_1f462); //  boot
        sEmojisMap.put("1f451", R.drawable.emoji_1f451); //  crown
        sEmojisMap.put("1f452", R.drawable.emoji_1f452); //  womans_hat
        sEmojisMap.put("1f3a9", R.drawable.emoji_1f3a9); //  tophat
        sEmojisMap.put("1f393", R.drawable.emoji_1f393); //  mortar_board
        sEmojisMap.put("1f9e2", R.drawable.emoji_1f9e2); //  billed_cap
        sEmojisMap.put("26d1", R.drawable.emoji_26d1); // helmet_with_white_cross
        // sEmojisMap.put("26d1_fe0f", R.drawable.emoji_26d1_fe0f); // helmet_with_white_cross
        sEmojisMap.put("1f4ff", R.drawable.emoji_1f4ff); //  prayer_beads
        sEmojisMap.put("1f484", R.drawable.emoji_1f484); //  lipstick
        sEmojisMap.put("1f48d", R.drawable.emoji_1f48d); //  ring
        sEmojisMap.put("1f48e", R.drawable.emoji_1f48e); //  gem
        sEmojisMap.put("1f507", R.drawable.emoji_1f507); //  mute
        sEmojisMap.put("1f508", R.drawable.emoji_1f508); //  speaker
        sEmojisMap.put("1f509", R.drawable.emoji_1f509); //  sound
        sEmojisMap.put("1f50a", R.drawable.emoji_1f50a); //  loud_sound
        sEmojisMap.put("1f4e2", R.drawable.emoji_1f4e2); //  loudspeaker
        sEmojisMap.put("1f4e3", R.drawable.emoji_1f4e3); //  mega
        sEmojisMap.put("1f4ef", R.drawable.emoji_1f4ef); //  postal_horn
        sEmojisMap.put("1f514", R.drawable.emoji_1f514); //  bell
        sEmojisMap.put("1f515", R.drawable.emoji_1f515); //  no_bell
        sEmojisMap.put("1f3bc", R.drawable.emoji_1f3bc); //  musical_score
        sEmojisMap.put("1f3b5", R.drawable.emoji_1f3b5); //  musical_note
        sEmojisMap.put("1f3b6", R.drawable.emoji_1f3b6); //  notes
        sEmojisMap.put("1f399_fe0f", R.drawable.emoji_1f399_fe0f); // studio_microphone
        sEmojisMap.put("1f39a_fe0f", R.drawable.emoji_1f39a_fe0f); // level_slider
        sEmojisMap.put("1f39b_fe0f", R.drawable.emoji_1f39b_fe0f); // control_knobs
        sEmojisMap.put("1f3a4", R.drawable.emoji_1f3a4); //  microphone
        sEmojisMap.put("1f3a7", R.drawable.emoji_1f3a7); //  headphones
        sEmojisMap.put("1f4fb", R.drawable.emoji_1f4fb); //  radio
        sEmojisMap.put("1f3b7", R.drawable.emoji_1f3b7); //  saxophone
        sEmojisMap.put("1f3b8", R.drawable.emoji_1f3b8); //  guitar
        sEmojisMap.put("1f3b9", R.drawable.emoji_1f3b9); //  musical_keyboard
        sEmojisMap.put("1f3ba", R.drawable.emoji_1f3ba); //  trumpet
        sEmojisMap.put("1f3bb", R.drawable.emoji_1f3bb); //  violin
        sEmojisMap.put("1fa95", R.drawable.emoji_1fa95); //  banjo
        sEmojisMap.put("1f941", R.drawable.emoji_1f941); //  drum_with_drumsticks
        sEmojisMap.put("1f4f1", R.drawable.emoji_1f4f1); //  iphone
        sEmojisMap.put("1f4f2", R.drawable.emoji_1f4f2); //  calling
        sEmojisMap.put("260e", R.drawable.emoji_260e); // phone|telephone
        // sEmojisMap.put("260e_fe0f", R.drawable.emoji_260e_fe0f); // phone|telephone
        sEmojisMap.put("1f4de", R.drawable.emoji_1f4de); //  telephone_receiver
        sEmojisMap.put("1f4df", R.drawable.emoji_1f4df); //  pager
        sEmojisMap.put("1f4e0", R.drawable.emoji_1f4e0); //  fax
        sEmojisMap.put("1f50b", R.drawable.emoji_1f50b); //  battery
        sEmojisMap.put("1f50c", R.drawable.emoji_1f50c); //  electric_plug
        sEmojisMap.put("1f4bb", R.drawable.emoji_1f4bb); //  computer
        sEmojisMap.put("1f5a5_fe0f", R.drawable.emoji_1f5a5_fe0f); // desktop_computer
        sEmojisMap.put("1f5a8_fe0f", R.drawable.emoji_1f5a8_fe0f); // printer
        sEmojisMap.put("2328", R.drawable.emoji_2328); // keyboard
        // sEmojisMap.put("2328_fe0f", R.drawable.emoji_2328_fe0f); // keyboard
        sEmojisMap.put("1f5b1_fe0f", R.drawable.emoji_1f5b1_fe0f); // three_button_mouse
        sEmojisMap.put("1f5b2_fe0f", R.drawable.emoji_1f5b2_fe0f); // trackball
        sEmojisMap.put("1f4bd", R.drawable.emoji_1f4bd); //  minidisc
        sEmojisMap.put("1f4be", R.drawable.emoji_1f4be); //  floppy_disk
        sEmojisMap.put("1f4bf", R.drawable.emoji_1f4bf); //  cd
        sEmojisMap.put("1f4c0", R.drawable.emoji_1f4c0); //  dvd
        sEmojisMap.put("1f9ee", R.drawable.emoji_1f9ee); //  abacus
        sEmojisMap.put("1f3a5", R.drawable.emoji_1f3a5); //  movie_camera
        sEmojisMap.put("1f39e_fe0f", R.drawable.emoji_1f39e_fe0f); // film_frames
        sEmojisMap.put("1f4fd_fe0f", R.drawable.emoji_1f4fd_fe0f); // film_projector
        sEmojisMap.put("1f3ac", R.drawable.emoji_1f3ac); //  clapper
        sEmojisMap.put("1f4fa", R.drawable.emoji_1f4fa); //  tv
        sEmojisMap.put("1f4f7", R.drawable.emoji_1f4f7); //  camera
        sEmojisMap.put("1f4f8", R.drawable.emoji_1f4f8); //  camera_with_flash
        sEmojisMap.put("1f4f9", R.drawable.emoji_1f4f9); //  video_camera
        sEmojisMap.put("1f4fc", R.drawable.emoji_1f4fc); //  vhs
        sEmojisMap.put("1f50d", R.drawable.emoji_1f50d); //  mag
        sEmojisMap.put("1f50e", R.drawable.emoji_1f50e); //  mag_right
        sEmojisMap.put("1f56f_fe0f", R.drawable.emoji_1f56f_fe0f); // candle
        sEmojisMap.put("1f4a1", R.drawable.emoji_1f4a1); //  bulb
        sEmojisMap.put("1f526", R.drawable.emoji_1f526); //  flashlight
        sEmojisMap.put("1f3ee", R.drawable.emoji_1f3ee); //  izakaya_lantern|lantern
        sEmojisMap.put("1fa94", R.drawable.emoji_1fa94); //  diya_lamp
        sEmojisMap.put("1f4d4", R.drawable.emoji_1f4d4); //  notebook_with_decorative_cover
        sEmojisMap.put("1f4d5", R.drawable.emoji_1f4d5); //  closed_book
        sEmojisMap.put("1f4d6", R.drawable.emoji_1f4d6); //  book|open_book
        sEmojisMap.put("1f4d7", R.drawable.emoji_1f4d7); //  green_book
        sEmojisMap.put("1f4d8", R.drawable.emoji_1f4d8); //  blue_book
        sEmojisMap.put("1f4d9", R.drawable.emoji_1f4d9); //  orange_book
        sEmojisMap.put("1f4da", R.drawable.emoji_1f4da); //  books
        sEmojisMap.put("1f4d3", R.drawable.emoji_1f4d3); //  notebook
        sEmojisMap.put("1f4d2", R.drawable.emoji_1f4d2); //  ledger
        sEmojisMap.put("1f4c3", R.drawable.emoji_1f4c3); //  page_with_curl
        sEmojisMap.put("1f4dc", R.drawable.emoji_1f4dc); //  scroll
        sEmojisMap.put("1f4c4", R.drawable.emoji_1f4c4); //  page_facing_up
        sEmojisMap.put("1f4f0", R.drawable.emoji_1f4f0); //  newspaper
        sEmojisMap.put("1f5de_fe0f", R.drawable.emoji_1f5de_fe0f); // rolled_up_newspaper
        sEmojisMap.put("1f4d1", R.drawable.emoji_1f4d1); //  bookmark_tabs
        sEmojisMap.put("1f516", R.drawable.emoji_1f516); //  bookmark
        sEmojisMap.put("1f3f7_fe0f", R.drawable.emoji_1f3f7_fe0f); // label
        sEmojisMap.put("1f4b0", R.drawable.emoji_1f4b0); //  moneybag
        sEmojisMap.put("1f4b4", R.drawable.emoji_1f4b4); //  yen
        sEmojisMap.put("1f4b5", R.drawable.emoji_1f4b5); //  dollar
        sEmojisMap.put("1f4b6", R.drawable.emoji_1f4b6); //  euro
        sEmojisMap.put("1f4b7", R.drawable.emoji_1f4b7); //  pound
        sEmojisMap.put("1f4b8", R.drawable.emoji_1f4b8); //  money_with_wings
        sEmojisMap.put("1f4b3", R.drawable.emoji_1f4b3); //  credit_card
        sEmojisMap.put("1f9fe", R.drawable.emoji_1f9fe); //  receipt
        sEmojisMap.put("1f4b9", R.drawable.emoji_1f4b9); //  chart
        sEmojisMap.put("1f4b1", R.drawable.emoji_1f4b1); //  currency_exchange
        sEmojisMap.put("1f4b2", R.drawable.emoji_1f4b2); //  heavy_dollar_sign
        sEmojisMap.put("270f", R.drawable.emoji_270f); // email|envelope
        // sEmojisMap.put("2709_fe0f", R.drawable.emoji_2709_fe0f); // email|envelope
        sEmojisMap.put("1f4e7", R.drawable.emoji_1f4e7); //  e-mail
        sEmojisMap.put("1f4e8", R.drawable.emoji_1f4e8); //  incoming_envelope
        sEmojisMap.put("1f4e9", R.drawable.emoji_1f4e9); //  envelope_with_arrow
        sEmojisMap.put("1f4e4", R.drawable.emoji_1f4e4); //  outbox_tray
        sEmojisMap.put("1f4e5", R.drawable.emoji_1f4e5); //  inbox_tray
        sEmojisMap.put("1f4e6", R.drawable.emoji_1f4e6); //  package
        sEmojisMap.put("1f4eb", R.drawable.emoji_1f4eb); //  mailbox
        sEmojisMap.put("1f4ea", R.drawable.emoji_1f4ea); //  mailbox_closed
        sEmojisMap.put("1f4ec", R.drawable.emoji_1f4ec); //  mailbox_with_mail
        sEmojisMap.put("1f4ed", R.drawable.emoji_1f4ed); //  mailbox_with_no_mail
        sEmojisMap.put("1f4ee", R.drawable.emoji_1f4ee); //  postbox
        sEmojisMap.put("1f5f3_fe0f", R.drawable.emoji_1f5f3_fe0f); // ballot_box_with_ballot
        sEmojisMap.put("270f", R.drawable.emoji_270f); // pencil2
        // sEmojisMap.put("270f_fe0f", R.drawable.emoji_270f_fe0f); // pencil2
        sEmojisMap.put("2712", R.drawable.emoji_2712); // black_nib
        // sEmojisMap.put("2712_fe0f", R.drawable.emoji_2712_fe0f); // black_nib
        sEmojisMap.put("1f58b_fe0f", R.drawable.emoji_1f58b_fe0f); // lower_left_fountain_pen
        sEmojisMap.put("1f58a_fe0f", R.drawable.emoji_1f58a_fe0f); // lower_left_ballpoint_pen
        sEmojisMap.put("1f58c_fe0f", R.drawable.emoji_1f58c_fe0f); // lower_left_paintbrush
        sEmojisMap.put("1f58d_fe0f", R.drawable.emoji_1f58d_fe0f); // lower_left_crayon
        sEmojisMap.put("1f4dd", R.drawable.emoji_1f4dd); //  memo|pencil
        sEmojisMap.put("1f4bc", R.drawable.emoji_1f4bc); //  briefcase
        sEmojisMap.put("1f4c1", R.drawable.emoji_1f4c1); //  file_folder
        sEmojisMap.put("1f4c2", R.drawable.emoji_1f4c2); //  open_file_folder
        sEmojisMap.put("1f5c2_fe0f", R.drawable.emoji_1f5c2_fe0f); // card_index_dividers
        sEmojisMap.put("1f4c5", R.drawable.emoji_1f4c5); //  date
        sEmojisMap.put("1f4c6", R.drawable.emoji_1f4c6); //  calendar
        sEmojisMap.put("1f5d2_fe0f", R.drawable.emoji_1f5d2_fe0f); // spiral_note_pad
        sEmojisMap.put("1f5d3_fe0f", R.drawable.emoji_1f5d3_fe0f); // spiral_calendar_pad
        sEmojisMap.put("1f4c7", R.drawable.emoji_1f4c7); //  card_index
        sEmojisMap.put("1f4c8", R.drawable.emoji_1f4c8); //  chart_with_upwards_trend
        sEmojisMap.put("1f4c9", R.drawable.emoji_1f4c9); //  chart_with_downwards_trend
        sEmojisMap.put("1f4ca", R.drawable.emoji_1f4ca); //  bar_chart
        sEmojisMap.put("1f4cb", R.drawable.emoji_1f4cb); //  clipboard
        sEmojisMap.put("1f4cc", R.drawable.emoji_1f4cc); //  pushpin
        sEmojisMap.put("1f4cd", R.drawable.emoji_1f4cd); //  round_pushpin
        sEmojisMap.put("1f4ce", R.drawable.emoji_1f4ce); //  paperclip
        sEmojisMap.put("1f587_fe0f", R.drawable.emoji_1f587_fe0f); // linked_paperclips
        sEmojisMap.put("1f4cf", R.drawable.emoji_1f4cf); //  straight_ruler
        sEmojisMap.put("1f4d0", R.drawable.emoji_1f4d0); //  triangular_ruler
        sEmojisMap.put("270f", R.drawable.emoji_270f); // scissors
        // sEmojisMap.put("2702_fe0f", R.drawable.emoji_2702_fe0f); // scissors
        sEmojisMap.put("1f5c3_fe0f", R.drawable.emoji_1f5c3_fe0f); // card_file_box
        sEmojisMap.put("1f5c4_fe0f", R.drawable.emoji_1f5c4_fe0f); // file_cabinet
        sEmojisMap.put("1f5d1_fe0f", R.drawable.emoji_1f5d1_fe0f); // wastebasket
        sEmojisMap.put("1f512", R.drawable.emoji_1f512); //  lock
        sEmojisMap.put("1f513", R.drawable.emoji_1f513); //  unlock
        sEmojisMap.put("1f50f", R.drawable.emoji_1f50f); //  lock_with_ink_pen
        sEmojisMap.put("1f510", R.drawable.emoji_1f510); //  closed_lock_with_key
        sEmojisMap.put("1f511", R.drawable.emoji_1f511); //  key
        sEmojisMap.put("1f5dd_fe0f", R.drawable.emoji_1f5dd_fe0f); // old_key
        sEmojisMap.put("1f528", R.drawable.emoji_1f528); //  hammer
        sEmojisMap.put("1fa93", R.drawable.emoji_1fa93); //  axe
        sEmojisMap.put("26cf", R.drawable.emoji_26cf); // pick
        // sEmojisMap.put("26cf_fe0f", R.drawable.emoji_26cf_fe0f); // pick
        sEmojisMap.put("2692", R.drawable.emoji_2692); // hammer_and_pick
        // sEmojisMap.put("2692_fe0f", R.drawable.emoji_2692_fe0f); // hammer_and_pick
        sEmojisMap.put("1f6e0_fe0f", R.drawable.emoji_1f6e0_fe0f); // hammer_and_wrench
        sEmojisMap.put("1f5e1_fe0f", R.drawable.emoji_1f5e1_fe0f); // dagger_knife
        sEmojisMap.put("2694", R.drawable.emoji_2694); // crossed_swords
        // sEmojisMap.put("2694_fe0f", R.drawable.emoji_2694_fe0f); // crossed_swords
        sEmojisMap.put("1f52b", R.drawable.emoji_1f52b); //  gun
        sEmojisMap.put("1f3f9", R.drawable.emoji_1f3f9); //  bow_and_arrow
        sEmojisMap.put("1f6e1_fe0f", R.drawable.emoji_1f6e1_fe0f); // shield
        sEmojisMap.put("1f527", R.drawable.emoji_1f527); //  wrench
        sEmojisMap.put("1f529", R.drawable.emoji_1f529); //  nut_and_bolt
        sEmojisMap.put("2699", R.drawable.emoji_2699); // gear
        // sEmojisMap.put("2699_fe0f", R.drawable.emoji_2699_fe0f); // gear
        sEmojisMap.put("1f5dc_fe0f", R.drawable.emoji_1f5dc_fe0f); // compression
        sEmojisMap.put("2696", R.drawable.emoji_2696); // scales
        // sEmojisMap.put("2696_fe0f", R.drawable.emoji_2696_fe0f); // scales
        sEmojisMap.put("1f9af", R.drawable.emoji_1f9af); //  probing_cane
        sEmojisMap.put("1f517", R.drawable.emoji_1f517); //  link
        sEmojisMap.put("26d3", R.drawable.emoji_26d3); // chains
        // sEmojisMap.put("26d3_fe0f", R.drawable.emoji_26d3_fe0f); // chains
        sEmojisMap.put("1f9f0", R.drawable.emoji_1f9f0); //  toolbox
        sEmojisMap.put("1f9f2", R.drawable.emoji_1f9f2); //  magnet
        sEmojisMap.put("2697", R.drawable.emoji_2697); // alembic
        // sEmojisMap.put("2697_fe0f", R.drawable.emoji_2697_fe0f); // alembic
        sEmojisMap.put("1f9ea", R.drawable.emoji_1f9ea); //  test_tube
        sEmojisMap.put("1f9eb", R.drawable.emoji_1f9eb); //  petri_dish
        sEmojisMap.put("1f9ec", R.drawable.emoji_1f9ec); //  dna
        sEmojisMap.put("1f52c", R.drawable.emoji_1f52c); //  microscope
        sEmojisMap.put("1f52d", R.drawable.emoji_1f52d); //  telescope
        sEmojisMap.put("1f4e1", R.drawable.emoji_1f4e1); //  satellite_antenna
        sEmojisMap.put("1f489", R.drawable.emoji_1f489); //  syringe
        sEmojisMap.put("1fa78", R.drawable.emoji_1fa78); //  drop_of_blood
        sEmojisMap.put("1f48a", R.drawable.emoji_1f48a); //  pill
        sEmojisMap.put("1fa79", R.drawable.emoji_1fa79); //  adhesive_bandage
        sEmojisMap.put("1fa7a", R.drawable.emoji_1fa7a); //  stethoscope
        sEmojisMap.put("1f6aa", R.drawable.emoji_1f6aa); //  door
        sEmojisMap.put("1f6cf_fe0f", R.drawable.emoji_1f6cf_fe0f); // bed
        sEmojisMap.put("1f6cb_fe0f", R.drawable.emoji_1f6cb_fe0f); // couch_and_lamp
        sEmojisMap.put("1fa91", R.drawable.emoji_1fa91); //  chair
        sEmojisMap.put("1f6bd", R.drawable.emoji_1f6bd); //  toilet
        sEmojisMap.put("1f6bf", R.drawable.emoji_1f6bf); //  shower
        sEmojisMap.put("1f6c1", R.drawable.emoji_1f6c1); //  bathtub
        sEmojisMap.put("1fa92", R.drawable.emoji_1fa92); //  razor
        sEmojisMap.put("1f9f4", R.drawable.emoji_1f9f4); //  lotion_bottle
        sEmojisMap.put("1f9f7", R.drawable.emoji_1f9f7); //  safety_pin
        sEmojisMap.put("1f9f9", R.drawable.emoji_1f9f9); //  broom
        sEmojisMap.put("1f9fa", R.drawable.emoji_1f9fa); //  basket
        sEmojisMap.put("1f9fb", R.drawable.emoji_1f9fb); //  roll_of_paper
        sEmojisMap.put("1f9fc", R.drawable.emoji_1f9fc); //  soap
        sEmojisMap.put("1f9fd", R.drawable.emoji_1f9fd); //  sponge
        sEmojisMap.put("1f9ef", R.drawable.emoji_1f9ef); //  fire_extinguisher
        sEmojisMap.put("1f6d2", R.drawable.emoji_1f6d2); //  shopping_trolley
        sEmojisMap.put("1f6ac", R.drawable.emoji_1f6ac); //  smoking
        sEmojisMap.put("26b0", R.drawable.emoji_26b0); // coffin
        // sEmojisMap.put("26b0_fe0f", R.drawable.emoji_26b0_fe0f); // coffin
        sEmojisMap.put("26b1", R.drawable.emoji_26b1); // funeral_urn
        // sEmojisMap.put("26b1_fe0f", R.drawable.emoji_26b1_fe0f); // funeral_urn
        sEmojisMap.put("1f5ff", R.drawable.emoji_1f5ff); //  moyai

        //symbols


        sEmojisMap.put("1f3e7", R.drawable.emoji_1f3e7); // atm
        sEmojisMap.put("1f6ae", R.drawable.emoji_1f6ae); // put_litter_in_its_place
        sEmojisMap.put("1f6b0", R.drawable.emoji_1f6b0); // potable_water
        sEmojisMap.put("267f", R.drawable.emoji_267f); // wheelchair
        sEmojisMap.put("1f6b9", R.drawable.emoji_1f6b9); // mens
        sEmojisMap.put("1f6ba", R.drawable.emoji_1f6ba); // womens
        sEmojisMap.put("1f6bb", R.drawable.emoji_1f6bb); // restroom
        sEmojisMap.put("1f6bc", R.drawable.emoji_1f6bc); // baby_symbol
        sEmojisMap.put("1f6be", R.drawable.emoji_1f6be); // wc
        sEmojisMap.put("1f6c2", R.drawable.emoji_1f6c2); // passport_control
        sEmojisMap.put("1f6c3", R.drawable.emoji_1f6c3); // customs
        sEmojisMap.put("1f6c4", R.drawable.emoji_1f6c4); // baggage_claim
        sEmojisMap.put("1f6c5", R.drawable.emoji_1f6c5); // left_luggage
        sEmojisMap.put("26a0", R.drawable.emoji_26a0); // warning
        // sEmojisMap.put("26a0_fe0f", R.drawable.emoji_26a0_fe0f); // warning
        sEmojisMap.put("1f6b8", R.drawable.emoji_1f6b8); // children_crossing
        sEmojisMap.put("26d4", R.drawable.emoji_26d4); // no_entry
        sEmojisMap.put("1f6ab", R.drawable.emoji_1f6ab); // no_entry_sign
        sEmojisMap.put("1f6b3", R.drawable.emoji_1f6b3); // no_bicycles
        sEmojisMap.put("1f6ad", R.drawable.emoji_1f6ad); // no_smoking
        sEmojisMap.put("1f6af", R.drawable.emoji_1f6af); // do_not_litter
        sEmojisMap.put("1f6b1", R.drawable.emoji_1f6b1); // non-potable_water
        sEmojisMap.put("1f6b7", R.drawable.emoji_1f6b7); // no_pedestrians
        sEmojisMap.put("1f4f5", R.drawable.emoji_1f4f5); // no_mobile_phones
        sEmojisMap.put("1f51e", R.drawable.emoji_1f51e); // underage
        sEmojisMap.put("2622", R.drawable.emoji_2622); // radioactive_sign
        // sEmojisMap.put("2622_fe0f", R.drawable.emoji_2622_fe0f); // radioactive_sign
        sEmojisMap.put("2623", R.drawable.emoji_2623); // biohazard_sign
        // sEmojisMap.put("2623_fe0f", R.drawable.emoji_2623_fe0f); // biohazard_sign
        sEmojisMap.put("2b06", R.drawable.emoji_2b06); // arrow_up
        // sEmojisMap.put("2b06_fe0f", R.drawable.emoji_2b06_fe0f); // arrow_up
        sEmojisMap.put("2197", R.drawable.emoji_2197); // arrow_upper_right
        // sEmojisMap.put("2197_fe0f", R.drawable.emoji_2197_fe0f); // arrow_upper_right
        sEmojisMap.put("27a1", R.drawable.emoji_27a1); // arrow_right
        // sEmojisMap.put("27a1_fe0f", R.drawable.emoji_27a1_fe0f); // arrow_right
        sEmojisMap.put("2198", R.drawable.emoji_2198); // arrow_lower_right
        // sEmojisMap.put("2198_fe0f", R.drawable.emoji_2198_fe0f); // arrow_lower_right
        sEmojisMap.put("2b07", R.drawable.emoji_2b07); // arrow_down
        // sEmojisMap.put("2b07_fe0f", R.drawable.emoji_2b07_fe0f); // arrow_down
        sEmojisMap.put("2199", R.drawable.emoji_2199); // arrow_lower_left
        // sEmojisMap.put("2199_fe0f", R.drawable.emoji_2199_fe0f); // arrow_lower_left
        sEmojisMap.put("2b05", R.drawable.emoji_2b05); // arrow_left
        // sEmojisMap.put("2b05_fe0f", R.drawable.emoji_2b05_fe0f); // arrow_left
        sEmojisMap.put("2196", R.drawable.emoji_2196); // arrow_upper_left
        // sEmojisMap.put("2196_fe0f", R.drawable.emoji_2196_fe0f); // arrow_upper_left
        sEmojisMap.put("2195", R.drawable.emoji_2195); // arrow_up_down
        // sEmojisMap.put("2195_fe0f", R.drawable.emoji_2195_fe0f); // arrow_up_down
        sEmojisMap.put("2194", R.drawable.emoji_2194); // left_right_arrow
        // sEmojisMap.put("2194_fe0f", R.drawable.emoji_2194_fe0f); // left_right_arrow
        sEmojisMap.put("21a9", R.drawable.emoji_21a9); // leftwards_arrow_with_hook
        // sEmojisMap.put("21a9_fe0f", R.drawable.emoji_21a9_fe0f); // leftwards_arrow_with_hook
        sEmojisMap.put("21aa", R.drawable.emoji_21aa); // arrow_right_hook
        // sEmojisMap.put("21aa_fe0f", R.drawable.emoji_21aa_fe0f); // arrow_right_hook
        sEmojisMap.put("2934", R.drawable.emoji_2934); // arrow_heading_up
        // sEmojisMap.put("2934_fe0f", R.drawable.emoji_2934_fe0f); // arrow_heading_up
        sEmojisMap.put("2935", R.drawable.emoji_2935); // arrow_heading_down
        // sEmojisMap.put("2935_fe0f", R.drawable.emoji_2935_fe0f); // arrow_heading_down
        sEmojisMap.put("1f503", R.drawable.emoji_1f503); // arrows_clockwise
        sEmojisMap.put("1f504", R.drawable.emoji_1f504); // arrows_counterclockwise
        sEmojisMap.put("1f519", R.drawable.emoji_1f519); // back
        sEmojisMap.put("1f51a", R.drawable.emoji_1f51a); // end
        sEmojisMap.put("1f51b", R.drawable.emoji_1f51b); // on
        sEmojisMap.put("1f51c", R.drawable.emoji_1f51c); // soon
        sEmojisMap.put("1f51d", R.drawable.emoji_1f51d); // top
        sEmojisMap.put("1f6d0", R.drawable.emoji_1f6d0); // place_of_worship
        sEmojisMap.put("269b", R.drawable.emoji_269b); // atom_symbol
        // sEmojisMap.put("269b_fe0f", R.drawable.emoji_269b_fe0f); // atom_symbol
        sEmojisMap.put("1f549_fe0f", R.drawable.emoji_1f549_fe0f); // om_symbol
        sEmojisMap.put("2721", R.drawable.emoji_2721); // star_of_david
        // sEmojisMap.put("2721_fe0f", R.drawable.emoji_2721_fe0f); // star_of_david
        sEmojisMap.put("2638", R.drawable.emoji_2638); // wheel_of_dharma
        // sEmojisMap.put("2638_fe0f", R.drawable.emoji_2638_fe0f); // wheel_of_dharma
        sEmojisMap.put("262f", R.drawable.emoji_262f); // yin_yang
        // sEmojisMap.put("262f_fe0f", R.drawable.emoji_262f_fe0f); // yin_yang
        sEmojisMap.put("271d", R.drawable.emoji_271d); // latin_cross
        // sEmojisMap.put("271d_fe0f", R.drawable.emoji_271d_fe0f); // latin_cross
        sEmojisMap.put("2626", R.drawable.emoji_2626); // orthodox_cross
        // sEmojisMap.put("2626_fe0f", R.drawable.emoji_2626_fe0f); // orthodox_cross
        sEmojisMap.put("262a", R.drawable.emoji_262a); // star_and_crescent
        // sEmojisMap.put("262a_fe0f", R.drawable.emoji_262a_fe0f); // star_and_crescent
        sEmojisMap.put("262e", R.drawable.emoji_262e); // peace_symbol
        // sEmojisMap.put("262e_fe0f", R.drawable.emoji_262e_fe0f); // peace_symbol
        sEmojisMap.put("1f54e", R.drawable.emoji_1f54e); // menorah_with_nine_branches
        sEmojisMap.put("1f52f", R.drawable.emoji_1f52f); // six_pointed_star
        sEmojisMap.put("2648", R.drawable.emoji_2648); // aries
        sEmojisMap.put("2649", R.drawable.emoji_2649); // taurus
        sEmojisMap.put("264a", R.drawable.emoji_264a); // gemini
        sEmojisMap.put("264b", R.drawable.emoji_264b); // cancer
        sEmojisMap.put("264c", R.drawable.emoji_264c); // leo
        sEmojisMap.put("264d", R.drawable.emoji_264d); // virgo
        sEmojisMap.put("264e", R.drawable.emoji_264e); // libra
        sEmojisMap.put("264f", R.drawable.emoji_264f); // scorpius
        sEmojisMap.put("2650", R.drawable.emoji_2650); // sagittarius
        sEmojisMap.put("2651", R.drawable.emoji_2651); // capricorn
        sEmojisMap.put("2652", R.drawable.emoji_2652); // aquarius
        sEmojisMap.put("2653", R.drawable.emoji_2653); // pisces
        sEmojisMap.put("26ce", R.drawable.emoji_26ce); // ophiuchus
        sEmojisMap.put("1f500", R.drawable.emoji_1f500); // twisted_rightwards_arrows
        sEmojisMap.put("1f501", R.drawable.emoji_1f501); // repeat
        sEmojisMap.put("1f502", R.drawable.emoji_1f502); // repeat_one
        sEmojisMap.put("25b6", R.drawable.emoji_25b6); // arrow_forward
        // sEmojisMap.put("25b6_fe0f", R.drawable.emoji_25b6_fe0f); // arrow_forward
        sEmojisMap.put("23e9", R.drawable.emoji_23e9); // fast_forward
        sEmojisMap.put("23ed", R.drawable.emoji_23ed); // black_right_pointing_double_triangle_with_vertical_bar
        // sEmojisMap.put("23ed_fe0f", R.drawable.emoji_23ed_fe0f); // black_right_pointing_double_triangle_with_vertical_bar
        sEmojisMap.put("23ef", R.drawable.emoji_23ef); // black_right_pointing_triangle_with_double_vertical_bar
        // sEmojisMap.put("23ef_fe0f", R.drawable.emoji_23ef_fe0f); // black_right_pointing_triangle_with_double_vertical_bar
        sEmojisMap.put("25c0", R.drawable.emoji_25c0); // arrow_backward
        // sEmojisMap.put("25c0_fe0f", R.drawable.emoji_25c0_fe0f); // arrow_backward
        sEmojisMap.put("23ea", R.drawable.emoji_23ea); // rewind
        sEmojisMap.put("23ee", R.drawable.emoji_23ee); // black_left_pointing_double_triangle_with_vertical_bar
        // sEmojisMap.put("23ee_fe0f", R.drawable.emoji_23ee_fe0f); // black_left_pointing_double_triangle_with_vertical_bar
        sEmojisMap.put("1f53c", R.drawable.emoji_1f53c); // arrow_up_small
        sEmojisMap.put("23eb", R.drawable.emoji_23eb); // arrow_double_up
        sEmojisMap.put("1f53d", R.drawable.emoji_1f53d); // arrow_down_small
        sEmojisMap.put("23ec", R.drawable.emoji_23ec); // arrow_double_down
        sEmojisMap.put("23f8", R.drawable.emoji_23f8); // double_vertical_bar
        // sEmojisMap.put("23f8_fe0f", R.drawable.emoji_23f8_fe0f); // double_vertical_bar
        sEmojisMap.put("23f9", R.drawable.emoji_23f9); // black_square_for_stop
        // sEmojisMap.put("23f9_fe0f", R.drawable.emoji_23f9_fe0f); // black_square_for_stop
        sEmojisMap.put("23fa", R.drawable.emoji_23fa); // black_circle_for_record
        // sEmojisMap.put("23fa_fe0f", R.drawable.emoji_23fa_fe0f); // black_circle_for_record
        sEmojisMap.put("23cf", R.drawable.emoji_23cf); // eject
        // sEmojisMap.put("23cf_fe0f", R.drawable.emoji_23cf_fe0f); // eject
        sEmojisMap.put("1f3a6", R.drawable.emoji_1f3a6); // cinema
        sEmojisMap.put("1f505", R.drawable.emoji_1f505); // low_brightness
        sEmojisMap.put("1f506", R.drawable.emoji_1f506); // high_brightness
        sEmojisMap.put("1f4f6", R.drawable.emoji_1f4f6); // signal_strength
        sEmojisMap.put("1f4f3", R.drawable.emoji_1f4f3); // vibration_mode
        sEmojisMap.put("1f4f4", R.drawable.emoji_1f4f4); // mobile_phone_off
        sEmojisMap.put("2640", R.drawable.emoji_2640); // female_sign
        // sEmojisMap.put("2640_fe0f", R.drawable.emoji_2640_fe0f); // female_sign
        sEmojisMap.put("2642", R.drawable.emoji_2642); // male_sign
        // sEmojisMap.put("2642_fe0f", R.drawable.emoji_2642_fe0f); // male_sign
        sEmojisMap.put("2695", R.drawable.emoji_2695); // medical_symbol|staff_of_aesculapius
        // sEmojisMap.put("2695_fe0f", R.drawable.emoji_2695_fe0f); // medical_symbol|staff_of_aesculapius
        sEmojisMap.put("267e", R.drawable.emoji_267e); // infinity
        // sEmojisMap.put("267e_fe0f", R.drawable.emoji_267e_fe0f); // infinity
        sEmojisMap.put("267b", R.drawable.emoji_267b); // recycle
        // sEmojisMap.put("267b_fe0f", R.drawable.emoji_267b_fe0f); // recycle
        sEmojisMap.put("269c", R.drawable.emoji_269c); // fleur_de_lis
        // sEmojisMap.put("269c_fe0f", R.drawable.emoji_269c_fe0f); // fleur_de_lis
        sEmojisMap.put("1f531", R.drawable.emoji_1f531); // trident
        sEmojisMap.put("1f4db", R.drawable.emoji_1f4db); // name_badge
        sEmojisMap.put("1f530", R.drawable.emoji_1f530); // beginner
        sEmojisMap.put("2b55", R.drawable.emoji_2b55); // o
        sEmojisMap.put("2705", R.drawable.emoji_2705); // white_check_mark
        sEmojisMap.put("2611", R.drawable.emoji_2611); // ballot_box_with_check
        // sEmojisMap.put("2611_fe0f", R.drawable.emoji_2611_fe0f); // ballot_box_with_check
        sEmojisMap.put("2714", R.drawable.emoji_2714); // heavy_check_mark
        // sEmojisMap.put("2714_fe0f", R.drawable.emoji_2714_fe0f); // heavy_check_mark
        sEmojisMap.put("2716", R.drawable.emoji_2716); // heavy_multiplication_x
        // sEmojisMap.put("2716_fe0f", R.drawable.emoji_2716_fe0f); // heavy_multiplication_x
        sEmojisMap.put("274c", R.drawable.emoji_274c); // x
        sEmojisMap.put("274e", R.drawable.emoji_274e); // negative_squared_cross_mark
        sEmojisMap.put("2795", R.drawable.emoji_2795); // heavy_plus_sign
        sEmojisMap.put("2796", R.drawable.emoji_2796); // heavy_minus_sign
        sEmojisMap.put("2797", R.drawable.emoji_2797); // heavy_division_sign
        sEmojisMap.put("27b0", R.drawable.emoji_27b0); // curly_loop
        sEmojisMap.put("27bf", R.drawable.emoji_27bf); // loop
        sEmojisMap.put("303d", R.drawable.emoji_303d); // part_alternation_mark
        // sEmojisMap.put("303d_fe0f", R.drawable.emoji_303d_fe0f); // part_alternation_mark
        sEmojisMap.put("2733", R.drawable.emoji_2733); // eight_spoked_asterisk
        // sEmojisMap.put("2733_fe0f", R.drawable.emoji_2733_fe0f); // eight_spoked_asterisk
        sEmojisMap.put("2734", R.drawable.emoji_2734); // eight_pointed_black_star
        // sEmojisMap.put("2734_fe0f", R.drawable.emoji_2734_fe0f); // eight_pointed_black_star
        sEmojisMap.put("2747", R.drawable.emoji_2747); // sparkle
        // sEmojisMap.put("2747_fe0f", R.drawable.emoji_2747_fe0f); // sparkle
        sEmojisMap.put("203c", R.drawable.emoji_203c); // bangbang
        // sEmojisMap.put("203c_fe0f", R.drawable.emoji_203c_fe0f); // bangbang
        sEmojisMap.put("2049", R.drawable.emoji_2049); // interrobang
        // sEmojisMap.put("2049_fe0f", R.drawable.emoji_2049_fe0f); // interrobang
        sEmojisMap.put("2753", R.drawable.emoji_2753); // question
        // sEmojisMap.put("2753", R.drawable.emoji_2753); // question
        sEmojisMap.put("2754", R.drawable.emoji_2754); // grey_question
        sEmojisMap.put("2755", R.drawable.emoji_2755); // grey_exclamation
        sEmojisMap.put("2757", R.drawable.emoji_2757); // exclamation|heavy_exclamation_mark
        sEmojisMap.put("3030", R.drawable.emoji_3030); // wavy_dash
        // sEmojisMap.put("3030_fe0f", R.drawable.emoji_3030_fe0f); // wavy_dash
        sEmojisMap.put("00a9", R.drawable.emoji_00a9); // copyright
        // sEmojisMap.put("00a9_fe0f", R.drawable.emoji_00a9_fe0f); // copyright
        sEmojisMap.put("00ae", R.drawable.emoji_00ae); // registered
        // sEmojisMap.put("00ae_fe0f", R.drawable.emoji_00ae_fe0f); // registered
        sEmojisMap.put("2122", R.drawable.emoji_2122); // tm
        // sEmojisMap.put("2122_fe0f", R.drawable.emoji_2122_fe0f); // tm
        sEmojisMap.put("0023", R.drawable.emoji_0023); // hash
        // sEmojisMap.put("0023", R.drawable.emoji_0023); // hash
        sEmojisMap.put("002a", R.drawable.emoji_002a); // keycap_star
        sEmojisMap.put("0030", R.drawable.emoji_0030); // zero
        // sEmojisMap.put("0030", R.drawable.emoji_0030); // zero
        sEmojisMap.put("0031", R.drawable.emoji_0031); // one
        // sEmojisMap.put("0031", R.drawable.emoji_0031); // one
        sEmojisMap.put("0032", R.drawable.emoji_0032); // two
        // sEmojisMap.put("0032", R.drawable.emoji_0032); // two
        sEmojisMap.put("0033", R.drawable.emoji_0033); // three
        // sEmojisMap.put("0033", R.drawable.emoji_0033); // three
        sEmojisMap.put("0034", R.drawable.emoji_0034); // four
        // sEmojisMap.put("0034", R.drawable.emoji_0034); // four
        sEmojisMap.put("0035", R.drawable.emoji_0035); // five
        // sEmojisMap.put("0035", R.drawable.emoji_0035); // five
        sEmojisMap.put("0036", R.drawable.emoji_0036); // six
        // sEmojisMap.put("0036", R.drawable.emoji_0036); // six
        sEmojisMap.put("0037", R.drawable.emoji_0037); // seven
        // sEmojisMap.put("0037", R.drawable.emoji_0037); // seven
        sEmojisMap.put("0038", R.drawable.emoji_0038); // eight
        // sEmojisMap.put("0038", R.drawable.emoji_0038); // eight
        sEmojisMap.put("0039", R.drawable.emoji_0039); // nine
        // sEmojisMap.put("0039", R.drawable.emoji_0039); // nine
        sEmojisMap.put("1f51f", R.drawable.emoji_1f51f); // keycap_ten
        sEmojisMap.put("1f520", R.drawable.emoji_1f520); // capital_abcd
        sEmojisMap.put("1f521", R.drawable.emoji_1f521); // abcd
        sEmojisMap.put("1f522", R.drawable.emoji_1f522); // 1234
        sEmojisMap.put("1f523", R.drawable.emoji_1f523); // symbols
        sEmojisMap.put("1f524", R.drawable.emoji_1f524); // abc
        sEmojisMap.put("1f17f", R.drawable.emoji_1f170); // a
        // sEmojisMap.put("1f170_fe0f", R.drawable.emoji_1f170_fe0f); // a
        sEmojisMap.put("1f18e", R.drawable.emoji_1f18e); // ab
        sEmojisMap.put("1f17f", R.drawable.emoji_1f171); // b
        // sEmojisMap.put("1f171_fe0f", R.drawable.emoji_1f171_fe0f); // b
        sEmojisMap.put("1f191", R.drawable.emoji_1f191); // cl
        sEmojisMap.put("1f192", R.drawable.emoji_1f192); // cool
        sEmojisMap.put("1f193", R.drawable.emoji_1f193); // free
        sEmojisMap.put("2139", R.drawable.emoji_2139); // information_source
        // sEmojisMap.put("2139_fe0f", R.drawable.emoji_2139_fe0f); // information_source
        sEmojisMap.put("1f194", R.drawable.emoji_1f194); // id
        sEmojisMap.put("24c2", R.drawable.emoji_24c2); // m
        // sEmojisMap.put("24c2_fe0f", R.drawable.emoji_24c2_fe0f); // m
        sEmojisMap.put("1f195", R.drawable.emoji_1f195); // new
        sEmojisMap.put("1f196", R.drawable.emoji_1f196); // ng
        sEmojisMap.put("1f17f", R.drawable.emoji_1f17e); // o2
        // sEmojisMap.put("1f17e_fe0f", R.drawable.emoji_1f17e_fe0f); // o2
        sEmojisMap.put("1f197", R.drawable.emoji_1f197); // ok
        sEmojisMap.put("1f17f", R.drawable.emoji_1f17f); // parking
        // sEmojisMap.put("1f17f_fe0f", R.drawable.emoji_1f17f_fe0f); // parking
        sEmojisMap.put("1f198", R.drawable.emoji_1f198); // sos
        sEmojisMap.put("1f199", R.drawable.emoji_1f199); // up
        sEmojisMap.put("1f19a", R.drawable.emoji_1f19a); // vs
        sEmojisMap.put("1f201", R.drawable.emoji_1f201); // koko
        sEmojisMap.put("1f20f", R.drawable.emoji_1f202); // sa
        // sEmojisMap.put("1f202_fe0f", R.drawable.emoji_1f202_fe0f); // sa
        sEmojisMap.put("1f23f", R.drawable.emoji_1f237); // u6708
        // sEmojisMap.put("1f237_fe0f", R.drawable.emoji_1f237_fe0f); // u6708
        sEmojisMap.put("1f236", R.drawable.emoji_1f236); // u6709
        sEmojisMap.put("1f22f", R.drawable.emoji_1f22f); // u6307
        sEmojisMap.put("1f250", R.drawable.emoji_1f250); // ideograph_advantage
        sEmojisMap.put("1f239", R.drawable.emoji_1f239); // u5272
        sEmojisMap.put("1f21a", R.drawable.emoji_1f21a); // u7121
        sEmojisMap.put("1f232", R.drawable.emoji_1f232); // u7981
        sEmojisMap.put("1f251", R.drawable.emoji_1f251); // accept
        sEmojisMap.put("1f238", R.drawable.emoji_1f238); // u7533
        sEmojisMap.put("1f234", R.drawable.emoji_1f234); // u5408
        sEmojisMap.put("1f233", R.drawable.emoji_1f233); // u7a7a
        sEmojisMap.put("3297", R.drawable.emoji_3297); // congratulations
        // sEmojisMap.put("3297_fe0f", R.drawable.emoji_3297_fe0f); // congratulations
        sEmojisMap.put("3299", R.drawable.emoji_3299); // secret
        // sEmojisMap.put("3299_fe0f", R.drawable.emoji_3299_fe0f); // secret
        sEmojisMap.put("1f23a", R.drawable.emoji_1f23a); // u55b6
        sEmojisMap.put("1f235", R.drawable.emoji_1f235); // u6e80
        sEmojisMap.put("1f534", R.drawable.emoji_1f534); // red_circle
        sEmojisMap.put("1f7e0", R.drawable.emoji_1f7e0); // large_orange_circle
        sEmojisMap.put("1f7e1", R.drawable.emoji_1f7e1); // large_yellow_circle
        sEmojisMap.put("1f7e2", R.drawable.emoji_1f7e2); // large_green_circle
        sEmojisMap.put("1f535", R.drawable.emoji_1f535); // large_blue_circle
        sEmojisMap.put("1f7e3", R.drawable.emoji_1f7e3); // large_purple_circle
        sEmojisMap.put("1f7e4", R.drawable.emoji_1f7e4); // large_brown_circle
        sEmojisMap.put("26ab", R.drawable.emoji_26ab); // black_circle
        sEmojisMap.put("26aa", R.drawable.emoji_26aa); // white_circle
        sEmojisMap.put("1f7e5", R.drawable.emoji_1f7e5); // large_red_square
        sEmojisMap.put("1f7e7", R.drawable.emoji_1f7e7); // large_orange_square
        sEmojisMap.put("1f7e8", R.drawable.emoji_1f7e8); // large_yellow_square
        sEmojisMap.put("1f7e9", R.drawable.emoji_1f7e9); // large_green_square
        sEmojisMap.put("1f7e6", R.drawable.emoji_1f7e6); // large_blue_square
        sEmojisMap.put("1f7ea", R.drawable.emoji_1f7ea); // large_purple_square
        sEmojisMap.put("1f7eb", R.drawable.emoji_1f7eb); // large_brown_square
        sEmojisMap.put("2b1b", R.drawable.emoji_2b1b); // black_large_square
        sEmojisMap.put("2b1c", R.drawable.emoji_2b1c); // white_large_square
        sEmojisMap.put("25fc", R.drawable.emoji_25fc); // black_medium_square
        // sEmojisMap.put("25fc_fe0f", R.drawable.emoji_25fc_fe0f); // black_medium_square
        sEmojisMap.put("25fb", R.drawable.emoji_25fb); // white_medium_square
        // sEmojisMap.put("25fb_fe0f", R.drawable.emoji_25fb_fe0f); // white_medium_square
        sEmojisMap.put("25fe", R.drawable.emoji_25fe); // black_medium_small_square
        sEmojisMap.put("25fd", R.drawable.emoji_25fd); // white_medium_small_square
        sEmojisMap.put("25aa", R.drawable.emoji_25aa); // black_small_square
        // sEmojisMap.put("25aa_fe0f", R.drawable.emoji_25aa_fe0f); // black_small_square
        sEmojisMap.put("25ab", R.drawable.emoji_25ab); // white_small_square
        // sEmojisMap.put("25ab_fe0f", R.drawable.emoji_25ab_fe0f); // white_small_square
        sEmojisMap.put("1f536", R.drawable.emoji_1f536); // large_orange_diamond
        sEmojisMap.put("1f537", R.drawable.emoji_1f537); // large_blue_diamond
        sEmojisMap.put("1f538", R.drawable.emoji_1f538); // small_orange_diamond
        sEmojisMap.put("1f539", R.drawable.emoji_1f539); // small_blue_diamond
        sEmojisMap.put("1f53a", R.drawable.emoji_1f53a); // small_red_triangle
        sEmojisMap.put("1f53b", R.drawable.emoji_1f53b); // small_red_triangle_down
        sEmojisMap.put("1f4a0", R.drawable.emoji_1f4a0); // diamond_shape_with_a_dot_inside
        sEmojisMap.put("1f518", R.drawable.emoji_1f518); // radio_button
        sEmojisMap.put("1f533", R.drawable.emoji_1f533); // white_square_button
        sEmojisMap.put("1f532", R.drawable.emoji_1f532); // black_square_button

        //flags
        sEmojisMap.put("1f3c1", R.drawable.emoji_1f3c1); //  checkered_flag
        sEmojisMap.put("1f6a9", R.drawable.emoji_1f6a9); //  triangular_flag_on_post
        sEmojisMap.put("1f38c", R.drawable.emoji_1f38c); //  crossed_flags
        sEmojisMap.put("1f3f4", R.drawable.emoji_1f3f4); //  waving_black_flag
        sEmojisMap.put("1f3f3_fe0f", R.drawable.emoji_1f3f3_fe0f); // waving_white_flag
        sEmojisMap.put("1f3f3_fe0f_200d_1f308", R.drawable.emoji_1f3f3_fe0f_200d_1f308); // rainbow-flag
        sEmojisMap.put("1f3f4_200d_2620_fe0f", R.drawable.emoji_1f3f4_200d_2620_fe0f); // pirate_flag
        sEmojisMap.put("1f1e6_1f1e8", R.drawable.emoji_1f1e6_1f1e8); // flag-ac
        sEmojisMap.put("1f1e6_1f1e9", R.drawable.emoji_1f1e6_1f1e9); // flag-ad
        sEmojisMap.put("1f1e6_1f1ea", R.drawable.emoji_1f1e6_1f1ea); // flag-ae
        sEmojisMap.put("1f1e6_1f1eb", R.drawable.emoji_1f1e6_1f1eb); // flag-af
        sEmojisMap.put("1f1e6_1f1ec", R.drawable.emoji_1f1e6_1f1ec); // flag-ag
        sEmojisMap.put("1f1e6_1f1ee", R.drawable.emoji_1f1e6_1f1ee); // flag-ai
        sEmojisMap.put("1f1e6_1f1f1", R.drawable.emoji_1f1e6_1f1f1); // flag-al
        sEmojisMap.put("1f1e6_1f1f2", R.drawable.emoji_1f1e6_1f1f2); // flag-am
        sEmojisMap.put("1f1e6_1f1f4", R.drawable.emoji_1f1e6_1f1f4); // flag-ao
        sEmojisMap.put("1f1e6_1f1f6", R.drawable.emoji_1f1e6_1f1f6); // flag-aq
        sEmojisMap.put("1f1e6_1f1f7", R.drawable.emoji_1f1e6_1f1f7); // flag-ar
        sEmojisMap.put("1f1e6_1f1f8", R.drawable.emoji_1f1e6_1f1f8); // flag-as
        sEmojisMap.put("1f1e6_1f1f9", R.drawable.emoji_1f1e6_1f1f9); // flag-at
        sEmojisMap.put("1f1e6_1f1fa", R.drawable.emoji_1f1e6_1f1fa); // flag-au
        sEmojisMap.put("1f1e6_1f1fc", R.drawable.emoji_1f1e6_1f1fc); // flag-aw
        sEmojisMap.put("1f1e6_1f1fd", R.drawable.emoji_1f1e6_1f1fd); // flag-ax
        sEmojisMap.put("1f1e6_1f1ff", R.drawable.emoji_1f1e6_1f1ff); // flag-az
        sEmojisMap.put("1f1e7_1f1e6", R.drawable.emoji_1f1e7_1f1e6); // flag-ba
        sEmojisMap.put("1f1e7_1f1e7", R.drawable.emoji_1f1e7_1f1e7); // flag-bb
        sEmojisMap.put("1f1e7_1f1e9", R.drawable.emoji_1f1e7_1f1e9); // flag-bd
        sEmojisMap.put("1f1e7_1f1ea", R.drawable.emoji_1f1e7_1f1ea); // flag-be
        sEmojisMap.put("1f1e7_1f1eb", R.drawable.emoji_1f1e7_1f1eb); // flag-bf
        sEmojisMap.put("1f1e7_1f1ec", R.drawable.emoji_1f1e7_1f1ec); // flag-bg
        sEmojisMap.put("1f1e7_1f1ed", R.drawable.emoji_1f1e7_1f1ed); // flag-bh
        sEmojisMap.put("1f1e7_1f1ee", R.drawable.emoji_1f1e7_1f1ee); // flag-bi
        sEmojisMap.put("1f1e7_1f1ef", R.drawable.emoji_1f1e7_1f1ef); // flag-bj
        sEmojisMap.put("1f1e7_1f1f1", R.drawable.emoji_1f1e7_1f1f1); // flag-bl
        sEmojisMap.put("1f1e7_1f1f2", R.drawable.emoji_1f1e7_1f1f2); // flag-bm
        sEmojisMap.put("1f1e7_1f1f3", R.drawable.emoji_1f1e7_1f1f3); // flag-bn
        sEmojisMap.put("1f1e7_1f1f4", R.drawable.emoji_1f1e7_1f1f4); // flag-bo
        sEmojisMap.put("1f1e7_1f1f6", R.drawable.emoji_1f1e7_1f1f6); // flag-bq
        sEmojisMap.put("1f1e7_1f1f7", R.drawable.emoji_1f1e7_1f1f7); // flag-br
        sEmojisMap.put("1f1e7_1f1f8", R.drawable.emoji_1f1e7_1f1f8); // flag-bs
        sEmojisMap.put("1f1e7_1f1f9", R.drawable.emoji_1f1e7_1f1f9); // flag-bt
        sEmojisMap.put("1f1e7_1f1fb", R.drawable.emoji_1f1e7_1f1fb); // flag-bv
        sEmojisMap.put("1f1e7_1f1fc", R.drawable.emoji_1f1e7_1f1fc); // flag-bw
        sEmojisMap.put("1f1e7_1f1fe", R.drawable.emoji_1f1e7_1f1fe); // flag-by
        sEmojisMap.put("1f1e7_1f1ff", R.drawable.emoji_1f1e7_1f1ff); // flag-bz
        sEmojisMap.put("1f1e8_1f1e6", R.drawable.emoji_1f1e8_1f1e6); // flag-ca
        sEmojisMap.put("1f1e8_1f1e8", R.drawable.emoji_1f1e8_1f1e8); // flag-cc
        sEmojisMap.put("1f1e8_1f1e9", R.drawable.emoji_1f1e8_1f1e9); // flag-cd
        sEmojisMap.put("1f1e8_1f1eb", R.drawable.emoji_1f1e8_1f1eb); // flag-cf
        sEmojisMap.put("1f1e8_1f1ec", R.drawable.emoji_1f1e8_1f1ec); // flag-cg
        sEmojisMap.put("1f1e8_1f1ed", R.drawable.emoji_1f1e8_1f1ed); // flag-ch
        sEmojisMap.put("1f1e8_1f1ee", R.drawable.emoji_1f1e8_1f1ee); // flag-ci
        sEmojisMap.put("1f1e8_1f1f0", R.drawable.emoji_1f1e8_1f1f0); // flag-ck
        sEmojisMap.put("1f1e8_1f1f1", R.drawable.emoji_1f1e8_1f1f1); // flag-cl
        sEmojisMap.put("1f1e8_1f1f2", R.drawable.emoji_1f1e8_1f1f2); // flag-cm
        sEmojisMap.put("1f1e8_1f1f3", R.drawable.emoji_1f1e8_1f1f3); // cn|flag-cn
        sEmojisMap.put("1f1e8_1f1f4", R.drawable.emoji_1f1e8_1f1f4); // flag-co
        sEmojisMap.put("1f1e8_1f1f5", R.drawable.emoji_1f1e8_1f1f5); // flag-cp
        sEmojisMap.put("1f1e8_1f1f7", R.drawable.emoji_1f1e8_1f1f7); // flag-cr
        sEmojisMap.put("1f1e8_1f1fa", R.drawable.emoji_1f1e8_1f1fa); // flag-cu
        sEmojisMap.put("1f1e8_1f1fb", R.drawable.emoji_1f1e8_1f1fb); // flag-cv
        sEmojisMap.put("1f1e8_1f1fc", R.drawable.emoji_1f1e8_1f1fc); // flag-cw
        sEmojisMap.put("1f1e8_1f1fd", R.drawable.emoji_1f1e8_1f1fd); // flag-cx
        sEmojisMap.put("1f1e8_1f1fe", R.drawable.emoji_1f1e8_1f1fe); // flag-cy
        sEmojisMap.put("1f1e8_1f1ff", R.drawable.emoji_1f1e8_1f1ff); // flag-cz
        sEmojisMap.put("1f1e9_1f1ea", R.drawable.emoji_1f1e9_1f1ea); // de|flag-de
        sEmojisMap.put("1f1e9_1f1ec", R.drawable.emoji_1f1e9_1f1ec); // flag-dg
        sEmojisMap.put("1f1e9_1f1ef", R.drawable.emoji_1f1e9_1f1ef); // flag-dj
        sEmojisMap.put("1f1e9_1f1f0", R.drawable.emoji_1f1e9_1f1f0); // flag-dk
        sEmojisMap.put("1f1e9_1f1f2", R.drawable.emoji_1f1e9_1f1f2); // flag-dm
        sEmojisMap.put("1f1e9_1f1f4", R.drawable.emoji_1f1e9_1f1f4); // flag-do
        sEmojisMap.put("1f1e9_1f1ff", R.drawable.emoji_1f1e9_1f1ff); // flag-dz
        sEmojisMap.put("1f1ea_1f1e6", R.drawable.emoji_1f1ea_1f1e6); // flag-ea
        sEmojisMap.put("1f1ea_1f1e8", R.drawable.emoji_1f1ea_1f1e8); // flag-ec
        sEmojisMap.put("1f1ea_1f1ea", R.drawable.emoji_1f1ea_1f1ea); // flag-ee
        sEmojisMap.put("1f1ea_1f1ec", R.drawable.emoji_1f1ea_1f1ec); // flag-eg
        sEmojisMap.put("1f1ea_1f1ed", R.drawable.emoji_1f1ea_1f1ed); // flag-eh
        sEmojisMap.put("1f1ea_1f1f7", R.drawable.emoji_1f1ea_1f1f7); // flag-er
        sEmojisMap.put("1f1ea_1f1f8", R.drawable.emoji_1f1ea_1f1f8); // es|flag-es
        sEmojisMap.put("1f1ea_1f1f9", R.drawable.emoji_1f1ea_1f1f9); // flag-et
        sEmojisMap.put("1f1ea_1f1fa", R.drawable.emoji_1f1ea_1f1fa); // flag-eu
        sEmojisMap.put("1f1eb_1f1ee", R.drawable.emoji_1f1eb_1f1ee); // flag-fi
        sEmojisMap.put("1f1eb_1f1ef", R.drawable.emoji_1f1eb_1f1ef); // flag-fj
        sEmojisMap.put("1f1eb_1f1f0", R.drawable.emoji_1f1eb_1f1f0); // flag-fk
        sEmojisMap.put("1f1eb_1f1f2", R.drawable.emoji_1f1eb_1f1f2); // flag-fm
        sEmojisMap.put("1f1eb_1f1f4", R.drawable.emoji_1f1eb_1f1f4); // flag-fo
        sEmojisMap.put("1f1eb_1f1f7", R.drawable.emoji_1f1eb_1f1f7); // fr|flag-fr
        sEmojisMap.put("1f1ec_1f1e6", R.drawable.emoji_1f1ec_1f1e6); // flag-ga
        sEmojisMap.put("1f1ec_1f1e7", R.drawable.emoji_1f1ec_1f1e7); // gb|uk|flag-gb
        sEmojisMap.put("1f1ec_1f1e9", R.drawable.emoji_1f1ec_1f1e9); // flag-gd
        sEmojisMap.put("1f1ec_1f1ea", R.drawable.emoji_1f1ec_1f1ea); // flag-ge
        sEmojisMap.put("1f1ec_1f1eb", R.drawable.emoji_1f1ec_1f1eb); // flag-gf
        sEmojisMap.put("1f1ec_1f1ec", R.drawable.emoji_1f1ec_1f1ec); // flag-gg
        sEmojisMap.put("1f1ec_1f1ed", R.drawable.emoji_1f1ec_1f1ed); // flag-gh
        sEmojisMap.put("1f1ec_1f1ee", R.drawable.emoji_1f1ec_1f1ee); // flag-gi
        sEmojisMap.put("1f1ec_1f1f1", R.drawable.emoji_1f1ec_1f1f1); // flag-gl
        sEmojisMap.put("1f1ec_1f1f2", R.drawable.emoji_1f1ec_1f1f2); // flag-gm
        sEmojisMap.put("1f1ec_1f1f3", R.drawable.emoji_1f1ec_1f1f3); // flag-gn
        sEmojisMap.put("1f1ec_1f1f5", R.drawable.emoji_1f1ec_1f1f5); // flag-gp
        sEmojisMap.put("1f1ec_1f1f6", R.drawable.emoji_1f1ec_1f1f6); // flag-gq
        sEmojisMap.put("1f1ec_1f1f7", R.drawable.emoji_1f1ec_1f1f7); // flag-gr
        sEmojisMap.put("1f1ec_1f1f8", R.drawable.emoji_1f1ec_1f1f8); // flag-gs
        sEmojisMap.put("1f1ec_1f1f9", R.drawable.emoji_1f1ec_1f1f9); // flag-gt
        sEmojisMap.put("1f1ec_1f1fa", R.drawable.emoji_1f1ec_1f1fa); // flag-gu
        sEmojisMap.put("1f1ec_1f1fc", R.drawable.emoji_1f1ec_1f1fc); // flag-gw
        sEmojisMap.put("1f1ec_1f1fe", R.drawable.emoji_1f1ec_1f1fe); // flag-gy
        sEmojisMap.put("1f1ed_1f1f0", R.drawable.emoji_1f1ed_1f1f0); // flag-hk
        sEmojisMap.put("1f1ed_1f1f2", R.drawable.emoji_1f1ed_1f1f2); // flag-hm
        sEmojisMap.put("1f1ed_1f1f3", R.drawable.emoji_1f1ed_1f1f3); // flag-hn
        sEmojisMap.put("1f1ed_1f1f7", R.drawable.emoji_1f1ed_1f1f7); // flag-hr
        sEmojisMap.put("1f1ed_1f1f9", R.drawable.emoji_1f1ed_1f1f9); // flag-ht
        sEmojisMap.put("1f1ed_1f1fa", R.drawable.emoji_1f1ed_1f1fa); // flag-hu
        sEmojisMap.put("1f1ee_1f1e8", R.drawable.emoji_1f1ee_1f1e8); // flag-ic
        sEmojisMap.put("1f1ee_1f1e9", R.drawable.emoji_1f1ee_1f1e9); // flag-id
        sEmojisMap.put("1f1ee_1f1ea", R.drawable.emoji_1f1ee_1f1ea); // flag-ie
        sEmojisMap.put("1f1ee_1f1f1", R.drawable.emoji_1f1ee_1f1f1); // flag-il
        sEmojisMap.put("1f1ee_1f1f2", R.drawable.emoji_1f1ee_1f1f2); // flag-im
        sEmojisMap.put("1f1ee_1f1f3", R.drawable.emoji_1f1ee_1f1f3); // flag-in
        sEmojisMap.put("1f1ee_1f1f4", R.drawable.emoji_1f1ee_1f1f4); // flag-io
        sEmojisMap.put("1f1ee_1f1f6", R.drawable.emoji_1f1ee_1f1f6); // flag-iq
        sEmojisMap.put("1f1ee_1f1f7", R.drawable.emoji_1f1ee_1f1f7); // flag-ir
        sEmojisMap.put("1f1ee_1f1f8", R.drawable.emoji_1f1ee_1f1f8); // flag-is
        sEmojisMap.put("1f1ee_1f1f9", R.drawable.emoji_1f1ee_1f1f9); // it|flag-it
        sEmojisMap.put("1f1ef_1f1ea", R.drawable.emoji_1f1ef_1f1ea); // flag-je
        sEmojisMap.put("1f1ef_1f1f2", R.drawable.emoji_1f1ef_1f1f2); // flag-jm
        sEmojisMap.put("1f1ef_1f1f4", R.drawable.emoji_1f1ef_1f1f4); // flag-jo
        sEmojisMap.put("1f1ef_1f1f5", R.drawable.emoji_1f1ef_1f1f5); // jp|flag-jp
        sEmojisMap.put("1f1f0_1f1ea", R.drawable.emoji_1f1f0_1f1ea); // flag-ke
        sEmojisMap.put("1f1f0_1f1ec", R.drawable.emoji_1f1f0_1f1ec); // flag-kg
        sEmojisMap.put("1f1f0_1f1ed", R.drawable.emoji_1f1f0_1f1ed); // flag-kh
        sEmojisMap.put("1f1f0_1f1ee", R.drawable.emoji_1f1f0_1f1ee); // flag-ki
        sEmojisMap.put("1f1f0_1f1f2", R.drawable.emoji_1f1f0_1f1f2); // flag-km
        sEmojisMap.put("1f1f0_1f1f3", R.drawable.emoji_1f1f0_1f1f3); // flag-kn
        sEmojisMap.put("1f1f0_1f1f5", R.drawable.emoji_1f1f0_1f1f5); // flag-kp
        sEmojisMap.put("1f1f0_1f1f7", R.drawable.emoji_1f1f0_1f1f7); // kr|flag-kr
        sEmojisMap.put("1f1f0_1f1fc", R.drawable.emoji_1f1f0_1f1fc); // flag-kw
        sEmojisMap.put("1f1f0_1f1fe", R.drawable.emoji_1f1f0_1f1fe); // flag-ky
        sEmojisMap.put("1f1f0_1f1ff", R.drawable.emoji_1f1f0_1f1ff); // flag-kz
        sEmojisMap.put("1f1f1_1f1e6", R.drawable.emoji_1f1f1_1f1e6); // flag-la
        sEmojisMap.put("1f1f1_1f1e7", R.drawable.emoji_1f1f1_1f1e7); // flag-lb
        sEmojisMap.put("1f1f1_1f1e8", R.drawable.emoji_1f1f1_1f1e8); // flag-lc
        sEmojisMap.put("1f1f1_1f1ee", R.drawable.emoji_1f1f1_1f1ee); // flag-li
        sEmojisMap.put("1f1f1_1f1f0", R.drawable.emoji_1f1f1_1f1f0); // flag-lk
        sEmojisMap.put("1f1f1_1f1f7", R.drawable.emoji_1f1f1_1f1f7); // flag-lr
        sEmojisMap.put("1f1f1_1f1f8", R.drawable.emoji_1f1f1_1f1f8); // flag-ls
        sEmojisMap.put("1f1f1_1f1f9", R.drawable.emoji_1f1f1_1f1f9); // flag-lt
        sEmojisMap.put("1f1f1_1f1fa", R.drawable.emoji_1f1f1_1f1fa); // flag-lu
        sEmojisMap.put("1f1f1_1f1fb", R.drawable.emoji_1f1f1_1f1fb); // flag-lv
        sEmojisMap.put("1f1f1_1f1fe", R.drawable.emoji_1f1f1_1f1fe); // flag-ly
        sEmojisMap.put("1f1f2_1f1e6", R.drawable.emoji_1f1f2_1f1e6); // flag-ma
        sEmojisMap.put("1f1f2_1f1e8", R.drawable.emoji_1f1f2_1f1e8); // flag-mc
        sEmojisMap.put("1f1f2_1f1e9", R.drawable.emoji_1f1f2_1f1e9); // flag-md
        sEmojisMap.put("1f1f2_1f1ea", R.drawable.emoji_1f1f2_1f1ea); // flag-me
        sEmojisMap.put("1f1f2_1f1eb", R.drawable.emoji_1f1f2_1f1eb); // flag-mf
        sEmojisMap.put("1f1f2_1f1ec", R.drawable.emoji_1f1f2_1f1ec); // flag-mg
        sEmojisMap.put("1f1f2_1f1ed", R.drawable.emoji_1f1f2_1f1ed); // flag-mh
        sEmojisMap.put("1f1f2_1f1f0", R.drawable.emoji_1f1f2_1f1f0); // flag-mk
        sEmojisMap.put("1f1f2_1f1f1", R.drawable.emoji_1f1f2_1f1f1); // flag-ml
        sEmojisMap.put("1f1f2_1f1f2", R.drawable.emoji_1f1f2_1f1f2); // flag-mm
        sEmojisMap.put("1f1f2_1f1f3", R.drawable.emoji_1f1f2_1f1f3); // flag-mn
        sEmojisMap.put("1f1f2_1f1f4", R.drawable.emoji_1f1f2_1f1f4); // flag-mo
        sEmojisMap.put("1f1f2_1f1f5", R.drawable.emoji_1f1f2_1f1f5); // flag-mp
        sEmojisMap.put("1f1f2_1f1f6", R.drawable.emoji_1f1f2_1f1f6); // flag-mq
        sEmojisMap.put("1f1f2_1f1f7", R.drawable.emoji_1f1f2_1f1f7); // flag-mr
        sEmojisMap.put("1f1f2_1f1f8", R.drawable.emoji_1f1f2_1f1f8); // flag-ms
        sEmojisMap.put("1f1f2_1f1f9", R.drawable.emoji_1f1f2_1f1f9); // flag-mt
        sEmojisMap.put("1f1f2_1f1fa", R.drawable.emoji_1f1f2_1f1fa); // flag-mu
        sEmojisMap.put("1f1f2_1f1fb", R.drawable.emoji_1f1f2_1f1fb); // flag-mv
        sEmojisMap.put("1f1f2_1f1fc", R.drawable.emoji_1f1f2_1f1fc); // flag-mw
        sEmojisMap.put("1f1f2_1f1fd", R.drawable.emoji_1f1f2_1f1fd); // flag-mx
        sEmojisMap.put("1f1f2_1f1fe", R.drawable.emoji_1f1f2_1f1fe); // flag-my
        sEmojisMap.put("1f1f2_1f1ff", R.drawable.emoji_1f1f2_1f1ff); // flag-mz
        sEmojisMap.put("1f1f3_1f1e6", R.drawable.emoji_1f1f3_1f1e6); // flag-na
        sEmojisMap.put("1f1f3_1f1e8", R.drawable.emoji_1f1f3_1f1e8); // flag-nc
        sEmojisMap.put("1f1f3_1f1ea", R.drawable.emoji_1f1f3_1f1ea); // flag-ne
        sEmojisMap.put("1f1f3_1f1eb", R.drawable.emoji_1f1f3_1f1eb); // flag-nf
        sEmojisMap.put("1f1f3_1f1ec", R.drawable.emoji_1f1f3_1f1ec); // flag-ng
        sEmojisMap.put("1f1f3_1f1ee", R.drawable.emoji_1f1f3_1f1ee); // flag-ni
        sEmojisMap.put("1f1f3_1f1f1", R.drawable.emoji_1f1f3_1f1f1); // flag-nl
        sEmojisMap.put("1f1f3_1f1f4", R.drawable.emoji_1f1f3_1f1f4); // flag-no
        sEmojisMap.put("1f1f3_1f1f5", R.drawable.emoji_1f1f3_1f1f5); // flag-np
        sEmojisMap.put("1f1f3_1f1f7", R.drawable.emoji_1f1f3_1f1f7); // flag-nr
        sEmojisMap.put("1f1f3_1f1fa", R.drawable.emoji_1f1f3_1f1fa); // flag-nu
        sEmojisMap.put("1f1f3_1f1ff", R.drawable.emoji_1f1f3_1f1ff); // flag-nz
        sEmojisMap.put("1f1f4_1f1f2", R.drawable.emoji_1f1f4_1f1f2); // flag-om
        sEmojisMap.put("1f1f5_1f1e6", R.drawable.emoji_1f1f5_1f1e6); // flag-pa
        sEmojisMap.put("1f1f5_1f1ea", R.drawable.emoji_1f1f5_1f1ea); // flag-pe
        sEmojisMap.put("1f1f5_1f1eb", R.drawable.emoji_1f1f5_1f1eb); // flag-pf
        sEmojisMap.put("1f1f5_1f1ec", R.drawable.emoji_1f1f5_1f1ec); // flag-pg
        sEmojisMap.put("1f1f5_1f1ed", R.drawable.emoji_1f1f5_1f1ed); // flag-ph
        sEmojisMap.put("1f1f5_1f1f0", R.drawable.emoji_1f1f5_1f1f0); // flag-pk
        sEmojisMap.put("1f1f5_1f1f1", R.drawable.emoji_1f1f5_1f1f1); // flag-pl
        sEmojisMap.put("1f1f5_1f1f2", R.drawable.emoji_1f1f5_1f1f2); // flag-pm
        sEmojisMap.put("1f1f5_1f1f3", R.drawable.emoji_1f1f5_1f1f3); // flag-pn
        sEmojisMap.put("1f1f5_1f1f7", R.drawable.emoji_1f1f5_1f1f7); // flag-pr
        sEmojisMap.put("1f1f5_1f1f8", R.drawable.emoji_1f1f5_1f1f8); // flag-ps
        sEmojisMap.put("1f1f5_1f1f9", R.drawable.emoji_1f1f5_1f1f9); // flag-pt
        sEmojisMap.put("1f1f5_1f1fc", R.drawable.emoji_1f1f5_1f1fc); // flag-pw
        sEmojisMap.put("1f1f5_1f1fe", R.drawable.emoji_1f1f5_1f1fe); // flag-py
        sEmojisMap.put("1f1f6_1f1e6", R.drawable.emoji_1f1f6_1f1e6); // flag-qa
        sEmojisMap.put("1f1f7_1f1ea", R.drawable.emoji_1f1f7_1f1ea); // flag-re
        sEmojisMap.put("1f1f7_1f1f4", R.drawable.emoji_1f1f7_1f1f4); // flag-ro
        sEmojisMap.put("1f1f7_1f1f8", R.drawable.emoji_1f1f7_1f1f8); // flag-rs
        sEmojisMap.put("1f1f7_1f1fa", R.drawable.emoji_1f1f7_1f1fa); // ru|flag-ru
        sEmojisMap.put("1f1f7_1f1fc", R.drawable.emoji_1f1f7_1f1fc); // flag-rw
        sEmojisMap.put("1f1f8_1f1e6", R.drawable.emoji_1f1f8_1f1e6); // flag-sa
        sEmojisMap.put("1f1f8_1f1e7", R.drawable.emoji_1f1f8_1f1e7); // flag-sb
        sEmojisMap.put("1f1f8_1f1e8", R.drawable.emoji_1f1f8_1f1e8); // flag-sc
        sEmojisMap.put("1f1f8_1f1e9", R.drawable.emoji_1f1f8_1f1e9); // flag-sd
        sEmojisMap.put("1f1f8_1f1ea", R.drawable.emoji_1f1f8_1f1ea); // flag-se
        sEmojisMap.put("1f1f8_1f1ec", R.drawable.emoji_1f1f8_1f1ec); // flag-sg
        sEmojisMap.put("1f1f8_1f1ed", R.drawable.emoji_1f1f8_1f1ed); // flag-sh
        sEmojisMap.put("1f1f8_1f1ee", R.drawable.emoji_1f1f8_1f1ee); // flag-si
        sEmojisMap.put("1f1f8_1f1ef", R.drawable.emoji_1f1f8_1f1ef); // flag-sj
        sEmojisMap.put("1f1f8_1f1f0", R.drawable.emoji_1f1f8_1f1f0); // flag-sk
        sEmojisMap.put("1f1f8_1f1f1", R.drawable.emoji_1f1f8_1f1f1); // flag-sl
        sEmojisMap.put("1f1f8_1f1f2", R.drawable.emoji_1f1f8_1f1f2); // flag-sm
        sEmojisMap.put("1f1f8_1f1f3", R.drawable.emoji_1f1f8_1f1f3); // flag-sn
        sEmojisMap.put("1f1f8_1f1f4", R.drawable.emoji_1f1f8_1f1f4); // flag-so
        sEmojisMap.put("1f1f8_1f1f7", R.drawable.emoji_1f1f8_1f1f7); // flag-sr
        sEmojisMap.put("1f1f8_1f1f8", R.drawable.emoji_1f1f8_1f1f8); // flag-ss
        sEmojisMap.put("1f1f8_1f1f9", R.drawable.emoji_1f1f8_1f1f9); // flag-st
        sEmojisMap.put("1f1f8_1f1fb", R.drawable.emoji_1f1f8_1f1fb); // flag-sv
        sEmojisMap.put("1f1f8_1f1fd", R.drawable.emoji_1f1f8_1f1fd); // flag-sx
        sEmojisMap.put("1f1f8_1f1fe", R.drawable.emoji_1f1f8_1f1fe); // flag-sy
        sEmojisMap.put("1f1f8_1f1ff", R.drawable.emoji_1f1f8_1f1ff); // flag-sz
        sEmojisMap.put("1f1f9_1f1e6", R.drawable.emoji_1f1f9_1f1e6); // flag-ta
        sEmojisMap.put("1f1f9_1f1e8", R.drawable.emoji_1f1f9_1f1e8); // flag-tc
        sEmojisMap.put("1f1f9_1f1e9", R.drawable.emoji_1f1f9_1f1e9); // flag-td
        sEmojisMap.put("1f1f9_1f1eb", R.drawable.emoji_1f1f9_1f1eb); // flag-tf
        sEmojisMap.put("1f1f9_1f1ec", R.drawable.emoji_1f1f9_1f1ec); // flag-tg
        sEmojisMap.put("1f1f9_1f1ed", R.drawable.emoji_1f1f9_1f1ed); // flag-th
        sEmojisMap.put("1f1f9_1f1ef", R.drawable.emoji_1f1f9_1f1ef); // flag-tj
        sEmojisMap.put("1f1f9_1f1f0", R.drawable.emoji_1f1f9_1f1f0); // flag-tk
        sEmojisMap.put("1f1f9_1f1f1", R.drawable.emoji_1f1f9_1f1f1); // flag-tl
        sEmojisMap.put("1f1f9_1f1f2", R.drawable.emoji_1f1f9_1f1f2); // flag-tm
        sEmojisMap.put("1f1f9_1f1f3", R.drawable.emoji_1f1f9_1f1f3); // flag-tn
        sEmojisMap.put("1f1f9_1f1f4", R.drawable.emoji_1f1f9_1f1f4); // flag-to
        sEmojisMap.put("1f1f9_1f1f7", R.drawable.emoji_1f1f9_1f1f7); // flag-tr
        sEmojisMap.put("1f1f9_1f1f9", R.drawable.emoji_1f1f9_1f1f9); // flag-tt
        sEmojisMap.put("1f1f9_1f1fb", R.drawable.emoji_1f1f9_1f1fb); // flag-tv
        sEmojisMap.put("1f1f9_1f1fc", R.drawable.emoji_1f1f9_1f1fc); // flag-tw
        sEmojisMap.put("1f1f9_1f1ff", R.drawable.emoji_1f1f9_1f1ff); // flag-tz
        sEmojisMap.put("1f1fa_1f1e6", R.drawable.emoji_1f1fa_1f1e6); // flag-ua
        sEmojisMap.put("1f1fa_1f1ec", R.drawable.emoji_1f1fa_1f1ec); // flag-ug
        sEmojisMap.put("1f1fa_1f1f2", R.drawable.emoji_1f1fa_1f1f2); // flag-um
        sEmojisMap.put("1f1fa_1f1f3", R.drawable.emoji_1f1fa_1f1f3); // flag-un
        sEmojisMap.put("1f1fa_1f1f8", R.drawable.emoji_1f1fa_1f1f8); // us|flag-us
        sEmojisMap.put("1f1fa_1f1fe", R.drawable.emoji_1f1fa_1f1fe); // flag-uy
        sEmojisMap.put("1f1fa_1f1ff", R.drawable.emoji_1f1fa_1f1ff); // flag-uz
        sEmojisMap.put("1f1fb_1f1e6", R.drawable.emoji_1f1fb_1f1e6); // flag-va
        sEmojisMap.put("1f1fb_1f1e8", R.drawable.emoji_1f1fb_1f1e8); // flag-vc
        sEmojisMap.put("1f1fb_1f1ea", R.drawable.emoji_1f1fb_1f1ea); // flag-ve
        sEmojisMap.put("1f1fb_1f1ec", R.drawable.emoji_1f1fb_1f1ec); // flag-vg
        sEmojisMap.put("1f1fb_1f1ee", R.drawable.emoji_1f1fb_1f1ee); // flag-vi
        sEmojisMap.put("1f1fb_1f1f3", R.drawable.emoji_1f1fb_1f1f3); // flag-vn
        sEmojisMap.put("1f1fb_1f1fa", R.drawable.emoji_1f1fb_1f1fa); // flag-vu
        sEmojisMap.put("1f1fc_1f1eb", R.drawable.emoji_1f1fc_1f1eb); // flag-wf
        sEmojisMap.put("1f1fc_1f1f8", R.drawable.emoji_1f1fc_1f1f8); // flag-ws
        sEmojisMap.put("1f1fd_1f1f0", R.drawable.emoji_1f1fd_1f1f0); // flag-xk
        sEmojisMap.put("1f1fe_1f1ea", R.drawable.emoji_1f1fe_1f1ea); // flag-ye
        sEmojisMap.put("1f1fe_1f1f9", R.drawable.emoji_1f1fe_1f1f9); // flag-yt
        sEmojisMap.put("1f1ff_1f1e6", R.drawable.emoji_1f1ff_1f1e6); // flag-za
        sEmojisMap.put("1f1ff_1f1f2", R.drawable.emoji_1f1ff_1f1f2); // flag-zm
        sEmojisMap.put("1f1ff_1f1fc", R.drawable.emoji_1f1ff_1f1fc); // flag-zw
        sEmojisMap.put("1f3f4_e0067_e0062_e0065_e006e_e0067_e007f", R.drawable.emoji_1f3f4_e0067_e0062_e0065_e006e_e0067_e007f); // flag-england
        sEmojisMap.put("1f3f4_e0067_e0062_e0073_e0063_e0074_e007f", R.drawable.emoji_1f3f4_e0067_e0062_e0073_e0063_e0074_e007f); // flag-scotland
        sEmojisMap.put("1f3f4_e0067_e0062_e0077_e006c_e0073_e007f", R.drawable.emoji_1f3f4_e0067_e0062_e0077_e006c_e0073_e007f); // flag-wales

    }

    private static int getEmojiResource(Context context, String codePoint) {
        return sEmojisMap.get(codePoint);
    }

    /**
     * Convert emoji characters of the given Spannable to the according emojicon.
     *
     * @param context
     * @param text
     * @param emojiSize
     */
    public static void addEmojis(Context context, Spannable text, int emojiSize) {
        addEmojis(context, text, emojiSize, 0, -1);
    }

    /**
     * Convert emoji characters of the given Spannable to the according emojicon.
     *
     * @param context
     * @param text
     * @param emojiSize
     * @param index
     * @param length
     */
    public static void addEmojis(Context context, Spannable text, int emojiSize, int index, int length) {
        int textLength = text.length();
        int textLengthToProcessMax = textLength - index;
        int textLengthToProcess = length < 0 || length >= textLengthToProcessMax ? textLength : (length + index);

        // remove spans throughout all text
        EmojiconSpan[] oldSpans = text.getSpans(0, textLength, EmojiconSpan.class);
        for (EmojiconSpan oldSpan : oldSpans) {
            text.removeSpan(oldSpan);
        }
        int skip;
        for (int i = index; i < textLengthToProcess; i += skip) {
            skip = 0;
            int icon = 0;
            char c = text.charAt(i);
            if (icon == 0) {
                int unicode = Character.codePointAt(text, i);
                skip = Character.charCount(unicode);
                if (unicode > 0xff) {
                    try {
                        icon = getEmojiResource(context, Integer.toHexString(unicode));
                    }
                    catch(NullPointerException e) {
                        System.out.println(icon+" "+Integer.toHexString(unicode));
                        throw e;
                    }
                }
                if (icon == 0 && i + skip < textLengthToProcess) {
                    int followUnicode = Character.codePointAt(text, i + skip);
                    if (followUnicode == 0x20e3) {
                        int followSkip = Character.charCount(followUnicode);
                        switch (unicode) {
                            case 0x0031: icon = R.drawable.emoji_0031; break;
                            case 0x0032: icon = R.drawable.emoji_0032; break;
                            case 0x0033: icon = R.drawable.emoji_0033; break;
                            case 0x0034: icon = R.drawable.emoji_0034; break;
                            case 0x0035: icon = R.drawable.emoji_0035; break;
                            case 0x0036: icon = R.drawable.emoji_0036; break;
                            case 0x0037: icon = R.drawable.emoji_0037; break;
                            case 0x0038: icon = R.drawable.emoji_0038; break;
                            case 0x0039: icon = R.drawable.emoji_0039; break;
                            case 0x0030: icon = R.drawable.emoji_0030; break;
                            case 0x0023: icon = R.drawable.emoji_0023; break;
                            default: followSkip = 0; break;
                        }
                        skip += followSkip;
                    }
                    else {
                        int followSkip = Character.charCount(followUnicode);
                        switch (unicode) {
                            case 0x1f1ef: icon = (followUnicode == 0x1f1f5) ? R.drawable.emoji_1f1ef_1f1f5 : 0; break;
                            case 0x1f1fa: icon = (followUnicode == 0x1f1f8) ? R.drawable.emoji_1f1fa_1f1f8 : 0; break;
                            case 0x1f1eb: icon = (followUnicode == 0x1f1f7) ? R.drawable.emoji_1f1eb_1f1f7 : 0; break;
                            case 0x1f1e9: icon = (followUnicode == 0x1f1ea) ? R.drawable.emoji_1f1e9_1f1ea : 0; break;
                            case 0x1f1ee: icon = (followUnicode == 0x1f1f9) ? R.drawable.emoji_1f1ee_1f1f9 : 0; break;
                            case 0x1f1ec: icon = (followUnicode == 0x1f1e7) ? R.drawable.emoji_1f1ec_1f1e7 : 0; break;
                            case 0x1f1ea: icon = (followUnicode == 0x1f1f8) ? R.drawable.emoji_1f1ea_1f1f8 : 0; break;
                            case 0x1f1f7: icon = (followUnicode == 0x1f1fa) ? R.drawable.emoji_1f1f7_1f1fa : 0; break;
                            case 0x1f1e8: icon = (followUnicode == 0x1f1f3) ? R.drawable.emoji_1f1e8_1f1f3 : 0; break;
                            case 0x1f1f0: icon = (followUnicode == 0x1f1f7) ? R.drawable.emoji_1f1f0_1f1f7 : 0; break;
                            default: followSkip = 0; break;
                        }
                        skip += followSkip;
                    }
                }
            }
            if (icon > 0) {
                text.setSpan(new EmojiconSpan(context, icon, emojiSize), i, i + skip, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
    }
}
