package com.halfplatepoha.jisho.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.text.TextUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by surjo on 21/04/17.
 */

public class Utils {

    private static final List<Character> hiragana = Arrays.asList(new Character[]{'あ', 'い','う', 'え', 'お',
            'か', 'き', 'く', 'け', 'こ',
            'た', 'ち', 'つ', 'て', 'と',
            'ら', 'り', 'る', 'れ', 'ろ',
            'な', 'に', 'ぬ', 'ね', 'の',
            'ま', 'み', 'む', 'め', 'も',
            'や', 'ゆ', 'よ', 'を',
            'は', 'ひ', 'ふ', 'へ', 'ほ',
            'が', 'ぎ', 'ぐ', 'げ', 'ご',
            'だ', 'ぢ', 'づ', 'で', 'ど',
            'ぱ', 'ぴ', 'ぷ', 'ぺ', 'ぽ',
            'ば', 'び', 'ぶ', 'べ', 'ぼ'});

    private static final List<Character> katakana = Arrays.asList(new Character[]{'ア', 'イ','ウ', 'エ', 'オ',
            'カ', 'キ', 'ク', 'ケ', 'コ',
            'タ', 'チ', 'ツ', 'テ', 'ト',
            'ラ', 'リ', 'ル', 'レ', 'ロ',
            'ナ', 'ニ', 'ヌ', 'ネ', 'ノ',
            'マ', 'イ', 'ム', 'メ', 'モ',
            'ヤ', 'ユ', 'ヨ', 'ヲ',
            'ハ', 'ヒ', 'フ', 'ヘ', 'ホ',
            'ガ', 'ギ', 'グ', 'ゲ', 'ゴ',
            'ダ', 'デ', 'ヅ', 'デ', 'ド',
            'パ', 'ピ', 'プ', 'ペ', 'ポ',
            'バ', 'ビ', 'ブ', 'ベ', 'ボ'});

    public static String getCommaSeparatedString(ArrayList<String> strings) {
        StringBuilder sb = new StringBuilder("");
        if(strings != null && !strings.isEmpty()) {
            for(int i=0; i<strings.size(); i++) {
                sb.append(strings.get(i));
                if(i != (strings.size() - 1))
                    sb.append(", ");
            }
        }

        return sb.toString();
    }

    public static ArrayList<String> kanjiList(String string) {
        ArrayList<String> kanjis = new ArrayList<>();
        if(!TextUtils.isEmpty(string)) {
            for(char c : string.toCharArray()) {
                if(!hiragana.contains(c) && !katakana.contains(c))
                    kanjis.add(String.valueOf(c));
            }
        }

        return kanjis;
    }


    public static boolean isFileDowloaded() {
        File externalDirectory = new File(IConstants.STORAGE_DIRECTORY);
        File dbFile = new File(externalDirectory, IConstants.DICTIONARY_FILE_NAME);

        if(externalDirectory.exists() && dbFile.exists()) {
            int totalFileSize = (int) (dbFile.length() / (Math.pow(1024, 2)));
            return (totalFileSize == IConstants.FILE_SIZE);
        }

        return false;
    }

    public static void deleteFile() {
        File externalDirectory = new File(IConstants.STORAGE_DIRECTORY);

        if(externalDirectory.exists()) {
            String[] children = externalDirectory.list();

            for (int i = 0; i < children.length; i++) {
                File child = new File(externalDirectory, children[i]);
                if (child.exists())
                    child.delete();
            }
            externalDirectory.delete();
        }
    }

    public static boolean checkInternetConnection(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }
}
