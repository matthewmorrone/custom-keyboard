package com.custom.keyboard.util;

import android.content.Context;
import android.media.MediaPlayer;

import com.custom.keyboard.R;

public class Sounds {
    public static void playIPA(Context context, int primaryCode) {
        int soundFileId = getIPA(primaryCode);
        final MediaPlayer mp = MediaPlayer.create(context, soundFileId);
        mp.start();
    }
    public static int getIPA(int primaryCode) {
        switch(primaryCode) {
            case 675:   return R.raw.consonant_voiced_alveolar_affricate;
            case 633:   return R.raw.consonant_voiced_alveolar_approximant;
            case 122:   return R.raw.consonant_voiced_alveolar_fricative;
            case 108:   return R.raw.consonant_voiced_alveolar_lateral_approximant;
            case 622:   return R.raw.consonant_voiced_alveolar_lateral_fricative;
            case 110:   return R.raw.consonant_voiced_alveolar_nasal;
            case 100:   return R.raw.consonant_voiced_alveolar_plosive;
            case 638:   return R.raw.consonant_voiced_alveolar_tap;
            case 114:   return R.raw.consonant_voiced_alveolar_trill;
            case 946:   return R.raw.consonant_voiced_bilabial_fricative;
            case 109:   return R.raw.consonant_voiced_bilabial_nasal;
            case 98:    return R.raw.consonant_voiced_bilabial_plosive;
            case 665:   return R.raw.consonant_voiced_bilabial_trill;
            case 240:   return R.raw.consonant_voiced_dental_fricative;
            case 674:   return R.raw.consonant_voiced_epiglottal_fricative;
            case 614:   return R.raw.consonant_voiced_glottal_fricative;
            case 613:   return R.raw.consonant_voiced_labial_velar_approximant;
            case 651:   return R.raw.consonant_voiced_labiodental_approximant;
            case 11377: return R.raw.consonant_voiced_labiodental_flap;
            case 118:   return R.raw.consonant_voiced_labiodental_fricative;
            case 625:   return R.raw.consonant_voiced_labiodental_nasal;
            case 106:   return R.raw.consonant_voiced_palatal_approximant;
            case 669:   return R.raw.consonant_voiced_palatal_fricative;
            case 654:   return R.raw.consonant_voiced_palatal_lateral_approximant;
            case 626:   return R.raw.consonant_voiced_palatal_nasal;
            case 607:   return R.raw.consonant_voiced_palatal_plosive;
            case 661:   return R.raw.consonant_voiced_pharyngeal_fricative;
            case 658:   return R.raw.consonant_voiced_postalveolar_fricative;
            case 635:   return R.raw.consonant_voiced_retroflex_approximant;
            case 656:   return R.raw.consonant_voiced_retroflex_fricative;
            case 621:   return R.raw.consonant_voiced_retroflex_lateral_approximant;
            case 627:   return R.raw.consonant_voiced_retroflex_nasal;
            case 598:   return R.raw.consonant_voiced_retroflex_plosive;
            case 637:   return R.raw.consonant_voiced_retroflex_tap;
            case 641:   return R.raw.consonant_voiced_uvular_fricative;
            case 628:   return R.raw.consonant_voiced_uvular_nasal;
            case 113:   return R.raw.consonant_voiced_uvular_plosive;
            case 640:   return R.raw.consonant_voiced_uvular_trill;
            case 624:   return R.raw.consonant_voiced_velar_approximant;
            case 611:   return R.raw.consonant_voiced_velar_fricative;
            case 671:   return R.raw.consonant_voiced_velar_lateral_approximant;
            case 331:   return R.raw.consonant_voiced_velar_nasal;
            case 609:   return R.raw.consonant_voiced_velar_plosive;
            case 115:   return R.raw.consonant_voiceless_alveolar_fricative;
            case 620:   return R.raw.consonant_voiceless_alveolar_lateral_fricative;
            case 116:   return R.raw.consonant_voiceless_alveolar_plosive;
            case 632:   return R.raw.consonant_voiceless_bilabial_fricative;
            case 112:   return R.raw.consonant_voiceless_bilabial_plosive;
            case 952:   return R.raw.consonant_voiceless_dental_fricative;
            case 668:   return R.raw.consonant_voiceless_epiglottal_fricative;
            case 104:   return R.raw.consonant_voiceless_glottal_fricative;
            case 660:   return R.raw.consonant_voiceless_glottal_plosive;
            case 102:   return R.raw.consonant_voiceless_labiodental_fricative;
            case 231:   return R.raw.consonant_voiceless_palatal_fricative;
            case 99:    return R.raw.consonant_voiceless_palatal_plosive;
            case 295:   return R.raw.consonant_voiceless_pharyngeal_fricative;
            case 643:   return R.raw.consonant_voiceless_postalveolar_fricative;
            case 642:   return R.raw.consonant_voiceless_retroflex_fricative;
            case 648:   return R.raw.consonant_voiceless_retroflex_plosive;
            case 967:   return R.raw.consonant_voiceless_uvular_fricative;
            case 610:   return R.raw.consonant_voiceless_uvular_plosive;
            case 107:   return R.raw.consonant_voiceless_velar_plosive;
            case 664:   return R.raw.non_pulmonic_voiceless_bilabial_click;
            case 448:   return R.raw.non_pulmonic_voiceless_dental_click;
            case 450:   return R.raw.non_pulmonic_voiceless_palatoalveolar_click;
            case 117:   return R.raw.vowel_close_back_rounded;
            case 623:   return R.raw.vowel_close_back_unrounded;
            case 649:   return R.raw.vowel_close_central_rounded;
            case 616:   return R.raw.vowel_close_central_unrounded;
            case 121:   return R.raw.vowel_close_front_rounded;
            case 105:   return R.raw.vowel_close_front_unrounded;
            case 111:   return R.raw.vowel_close_mid_back_rounded;
            case 612:   return R.raw.vowel_close_mid_back_unrounded;
            case 629:   return R.raw.vowel_close_mid_central_rounded;
            case 600:   return R.raw.vowel_close_mid_central_unrounded;
            case 248:   return R.raw.vowel_close_mid_front_rounded;
            case 101:   return R.raw.vowel_close_mid_front_unrounded;
            case 601:   return R.raw.vowel_mid_central;
            case 650:   return R.raw.vowel_near_close_near_back_rounded;
            case 655:   return R.raw.vowel_near_close_near_front_rounded;
            case 618:   return R.raw.vowel_near_close_near_front_unrounded;
            case 592:   return R.raw.vowel_near_open_central;
            case 230:   return R.raw.vowel_near_open_front_unrounded;
            case 594:   return R.raw.vowel_open_back_rounded;
            case 593:   return R.raw.vowel_open_back_unrounded;
            case 630:   return R.raw.vowel_open_front_rounded;
            case 97:    return R.raw.vowel_open_front_unrounded;
            case 596:   return R.raw.vowel_open_mid_back_rounded;
            case 652:   return R.raw.vowel_open_mid_back_unrounded;
            case 606:   return R.raw.vowel_open_mid_central_rounded;
            case 604:   return R.raw.vowel_open_mid_central_unrounded;
            case 339:   return R.raw.vowel_open_mid_front_rounded;
            case 603:   return R.raw.vowel_open_mid_front_unrounded;
        }
        return -1;
    }
    public static String getDescription(int primaryCode) {
        switch(primaryCode) {
            case 675:   return "Consonant: Voiced Alveolar Affricate";
            case 633:   return "Consonant: Voiced Alveolar Approximant";
            case 122:   return "Consonant: Voiced Alveolar Fricative";
            case 108:   return "Consonant: Voiced Alveolar Lateral Approximant";
            case 622:   return "Consonant: Voiced Alveolar Lateral Fricative";
            case 110:   return "Consonant: Voiced Alveolar Nasal";
            case 100:   return "Consonant: Voiced Alveolar Plosive";
            case 638:   return "Consonant: Voiced Alveolar Tap";
            case 114:   return "Consonant: Voiced Alveolar Trill";
            case 946:   return "Consonant: Voiced Bilabial Fricative";
            case 109:   return "Consonant: Voiced Bilabial Nasal";
            case 98:    return "Consonant: Voiced Bilabial Plosive";
            case 665:   return "Consonant: Voiced Bilabial Trill";
            case 240:   return "Consonant: Voiced Dental Fricative";
            case 674:   return "Consonant: Voiced Epiglottal Fricative";
            case 614:   return "Consonant: Voiced Glottal Fricative";
            case 613:   return "Consonant: Voiced Labial Velar Approximant";
            case 651:   return "Consonant: Voiced Labiodental Approximant";
            case 11377: return "Consonant: Voiced Labiodental Flap";
            case 118:   return "Consonant: Voiced Labiodental Fricative";
            case 625:   return "Consonant: Voiced Labiodental Nasal";
            case 106:   return "Consonant: Voiced Palatal Approximant";
            case 669:   return "Consonant: Voiced Palatal Fricative";
            case 654:   return "Consonant: Voiced Palatal Lateral Approximant";
            case 626:   return "Consonant: Voiced Palatal Nasal";
            case 607:   return "Consonant: Voiced Palatal Plosive";
            case 661:   return "Consonant: Voiced Pharyngeal Fricative";
            case 658:   return "Consonant: Voiced Postalveolar Fricative";
            case 635:   return "Consonant: Voiced Retroflex Approximant";
            case 656:   return "Consonant: Voiced Retroflex Fricative";
            case 621:   return "Consonant: Voiced Retroflex Lateral Approximant";
            case 627:   return "Consonant: Voiced Retroflex Nasal";
            case 598:   return "Consonant: Voiced Retroflex Plosive";
            case 637:   return "Consonant: Voiced Retroflex Tap";
            case 641:   return "Consonant: Voiced Uvular Fricative";
            case 628:   return "Consonant: Voiced Uvular Nasal";
            case 113:   return "Consonant: Voiced Uvular Plosive";
            case 640:   return "Consonant: Voiced Uvular Trill";
            case 624:   return "Consonant: Voiced Velar Approximant";
            case 611:   return "Consonant: Voiced Velar Fricative";
            case 671:   return "Consonant: Voiced Velar Lateral Approximant";
            case 331:   return "Consonant: Voiced Velar Nasal";
            case 609:   return "Consonant: Voiced Velar Plosive";
            case 115:   return "Consonant: Voiceless Alveolar Fricative";
            case 620:   return "Consonant: Voiceless Alveolar Lateral Fricative";
            case 116:   return "Consonant: Voiceless Alveolar Plosive";
            case 632:   return "Consonant: Voiceless Bilabial Fricative";
            case 112:   return "Consonant: Voiceless Bilabial Plosive";
            case 952:   return "Consonant: Voiceless Dental Fricative";
            case 668:   return "Consonant: Voiceless Epiglottal Fricative";
            case 104:   return "Consonant: Voiceless Glottal Fricative";
            case 660:   return "Consonant: Voiceless Glottal Plosive";
            case 102:   return "Consonant: Voiceless Labiodental Fricative";
            case 231:   return "Consonant: Voiceless Palatal Fricative";
            case 99:    return "Consonant: Voiceless Palatal Plosive";
            case 295:   return "Consonant: Voiceless Pharyngeal Fricative";
            case 643:   return "Consonant: Voiceless Postalveolar Fricative";
            case 642:   return "Consonant: Voiceless Retroflex Fricative";
            case 648:   return "Consonant: Voiceless Retroflex Plosive";
            case 967:   return "Consonant: Voiceless Uvular Fricative";
            case 610:   return "Consonant: Voiceless Uvular Plosive";
            case 107:   return "Consonant: Voiceless Velar Plosive";
            case 664:   return "Nonpulmonic: Voiceless Bilabial Click";
            case 448:   return "Nonpulmonic: Voiceless Dental Click";
            case 450:   return "Nonpulmonic: Voiceless Palatoalveolar Click";
            case 117:   return "Vowel: Close Back Rounded";
            case 623:   return "Vowel: Close Back Unrounded";
            case 649:   return "Vowel: Close Central Rounded";
            case 616:   return "Vowel: Close Central Unrounded";
            case 121:   return "Vowel: Close Front Rounded";
            case 105:   return "Vowel: Close Front Unrounded";
            case 111:   return "Vowel: Close Mid Back Rounded";
            case 612:   return "Vowel: Close Mid Back Unrounded";
            case 629:   return "Vowel: Close Mid Central Rounded";
            case 600:   return "Vowel: Close Mid Central Unrounded";
            case 248:   return "Vowel: Close Mid Front Rounded";
            case 101:   return "Vowel: Close Mid Front Unrounded";
            case 601:   return "Vowel: Mid Central";
            case 650:   return "Vowel: Near Close Near Back Rounded";
            case 655:   return "Vowel: Near Close Near Front Rounded";
            case 618:   return "Vowel: Near Close Near Front Unrounded";
            case 592:   return "Vowel: Near Open Central";
            case 230:   return "Vowel: Near Open Front Unrounded";
            case 594:   return "Vowel: Open Back Rounded";
            case 593:   return "Vowel: Open Back Unrounded";
            case 630:   return "Vowel: Open Front Rounded";
            case 97:    return "Vowel: Open Front Unrounded";
            case 596:   return "Vowel: Open Mid Back Rounded";
            case 652:   return "Vowel: Open Mid Back Unrounded";
            case 606:   return "Vowel: Open Mid Central Rounded";
            case 604:   return "Vowel: Open Mid Central Unrounded";
            case 339:   return "Vowel: Open Mid Front Rounded";
            case 603:   return "Vowel: Open Mid Front Unrounded";
        }
        return "";
    }
}
