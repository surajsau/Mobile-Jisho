package com.halfplatepoha.jisho.utils;

public class VerbInflectionUtils {

    private StringBuilder affirmative;
    private StringBuilder negative;
    private
    @VerbInflection.Type String pos;
    private String mRoot;

    private static final String SA = "さ";
    private static final String RO = "ろ";
    private static final String RA = "ら";
    private static final String RARE = "られ";
    private static final String NA = "な";
    private static final String RU = "る";
    private static final String NAI = "ない";
    private static final String NAKATTA = "なかった";
    private static final String MASU = "ます";
    private static final String MASEN = "ません";
    private static final String TA = "た";
    private static final String DA = "だ";
    private static final String MASHITA = "ました";
    private static final String DESHITA = "でした";
    private static final String TE = "て";
    private static final String DE = "で";
    private static final String NAKUTE = "なくて";
    private static final String RERU = "れる";
    private static final String RENAI = "れない";
    private static final String SERU = "せる";
    private static final String SENAI = "せない";
    private static final String SERARERU = "せられる";
    private static final String SERARENAI = "せられない";

    /**
     * Constructur
     *
     * @param root mRoot verb (e.g., iku)
     * @param pos  parts_of_speech (e.g., iku is godan verb)
     */
    public VerbInflectionUtils(String root, @VerbInflection.Type String pos) {
        this.mRoot = root;
        this.pos = pos;
    }

    public String getAffirmative() {
        return affirmative.toString();
    }

    public String getNegative() {
        return negative.toString();
    }

    public VerbInflection getNonPast() {
        return new VerbInflection(mRoot.toString(),
                getNegativeStem(mRoot).append(NAI).toString());
    }

    public VerbInflection getNonPastPolite() {
        return new VerbInflection(getPoliteStem(mRoot).append(MASU).toString(),
                getPoliteStem(mRoot).append(MASEN).toString());
    }

    public VerbInflection getPast() {
        StringBuilder teStem = getTeStem(mRoot);
        boolean isNEnding = "ん".equalsIgnoreCase(teStem.substring(teStem.length() - 1));

        return new VerbInflection(teStem.append(isNEnding ? DA : TA).toString(),
                getNegativeStem(mRoot).append(NAKATTA).toString());
    }

    public VerbInflection getPastPolite() {
        return new VerbInflection(getPoliteStem(mRoot).append(MASHITA).toString(),
                getPoliteStem(mRoot).append(MASEN).append(DESHITA).toString());
    }

    public VerbInflection getTeForm() {
        StringBuilder teStem = getTeStem(mRoot);
        boolean isNEnding = "ん".equalsIgnoreCase(teStem.substring(teStem.length() - 1));

        return new VerbInflection(teStem.append(isNEnding ? DE : TE).toString(),
                getNegativeStem(mRoot).append(NAKUTE).toString());
    }

    public VerbInflection getPotential() {
        return new VerbInflection(getPotentialStem(mRoot).append(RU).toString(),
                getPotentialStem(mRoot).append(NAI).toString());
    }

    public VerbInflection getPassive() {
        return new VerbInflection(getPassiveStem(mRoot).append(RERU).toString(),
                getPassiveStem(mRoot).append(RENAI).toString());
    }

    public VerbInflection getCausative() {
        return new VerbInflection(getCausativeStem(mRoot).append(SERU).toString(),
                getCausativeStem(mRoot).append(SENAI).toString());
    }

    public VerbInflection getCausativePassive() {
        return new VerbInflection(getCausativeStem(mRoot).append(SERARERU).toString(),
                getCausativeStem(mRoot).append(SERARENAI).toString());
    }

    public VerbInflection getImperative() {
        return new VerbInflection(getImperativeStem(mRoot).toString(),
                new StringBuilder(mRoot).append(NA).toString());
    }

    private StringBuilder getNegativeStem(String root) {
        int len = root.length();
        StringBuilder sb = new StringBuilder(root);
        switch (pos) {
            case VerbInflection.TYPE_SURU_VERB:
                return new StringBuilder("し");
            case VerbInflection.TYPE_KURU_VERB:
                return new StringBuilder("来");
            case VerbInflection.TYPE_ICHIDAN:
                return sb.deleteCharAt(len - 1);
            case VerbInflection.TYPE_GODAN:
                String last = sb.substring(len - 1);
                return sb.replace(len - 1, len, getNegativeReplacement(last));
        }
        return sb;
    }

    private StringBuilder getPassiveStem(String root) {
        int len = root.length();
        StringBuilder sb = new StringBuilder(root);
        switch (pos) {
            case VerbInflection.TYPE_SURU_VERB:
                return new StringBuilder("さ");
            case VerbInflection.TYPE_KURU_VERB:
                return new StringBuilder("来").append(RA);
            case VerbInflection.TYPE_ICHIDAN:
                return sb.deleteCharAt(len - 1).append(RA);
            case VerbInflection.TYPE_GODAN:
                String last = sb.substring(len - 1);
                return sb.replace(len - 1, len, getNegativeReplacement(last));
        }
        return sb;
    }

    private StringBuilder getPoliteStem(String root) {
        int len = root.length();
        StringBuilder sb = new StringBuilder(root);
        switch (pos) {
            case VerbInflection.TYPE_SURU_VERB:
                return new StringBuilder("し");
            case VerbInflection.TYPE_KURU_VERB:
                return new StringBuilder("来");
            case VerbInflection.TYPE_ICHIDAN:
                return sb.deleteCharAt(len - 1);
            case VerbInflection.TYPE_GODAN:
                String last = sb.substring(len - 1);
                return sb.replace(len - 1, len, getPoliteReplacement(last));
        }
        return sb;
    }

    private StringBuilder getPotentialStem(String root) {
        int len = root.length();
        StringBuilder sb = new StringBuilder(root);
        switch (pos) {
            case VerbInflection.TYPE_SURU_VERB:
                return new StringBuilder("でき");
            case VerbInflection.TYPE_KURU_VERB:
                return new StringBuilder("来").append(RARE);
            case VerbInflection.TYPE_ICHIDAN:
                return sb.deleteCharAt(len - 1).append(RARE);
            case VerbInflection.TYPE_GODAN:
                String last = sb.substring(len - 1);
                return sb.replace(len - 1, len, getPotentialReplacement(last));
        }
        return sb;
    }

    private StringBuilder getCausativeStem(String root) {
        int len = root.length();
        StringBuilder sb = new StringBuilder(root);
        switch (pos) {
            case VerbInflection.TYPE_SURU_VERB:
                return new StringBuilder("さ");
            case VerbInflection.TYPE_KURU_VERB:
                return new StringBuilder("来").append(SA);
            case VerbInflection.TYPE_ICHIDAN:
                return sb.deleteCharAt(len - 1).append(SA);
            case VerbInflection.TYPE_GODAN:
                String last = sb.substring(len - 1);
                return sb.replace(len - 1, len, getNegativeReplacement(last));
        }
        return sb;
    }

    private StringBuilder getTeStem(String root) {
        int len = root.length();
        StringBuilder sb = new StringBuilder(root);
        switch (pos) {
            case VerbInflection.TYPE_SURU_VERB:
                return new StringBuilder("し");
            case VerbInflection.TYPE_KURU_VERB:
                return new StringBuilder("来");
            case VerbInflection.TYPE_ICHIDAN:
                return sb.deleteCharAt(len - 1);
            case VerbInflection.TYPE_GODAN:
                String last = sb.substring(len - 1);
                return sb.replace(len - 1, len, getTeStemReplacement(last));
        }
        return sb;
    }

    private StringBuilder getImperativeStem(String root) {
        int len = root.length();
        StringBuilder sb = new StringBuilder(root);
        switch (pos) {
            case VerbInflection.TYPE_SURU_VERB:
                return new StringBuilder("しろ");
            case VerbInflection.TYPE_KURU_VERB:
                return new StringBuilder("来い");
            case VerbInflection.TYPE_ICHIDAN:
                return sb.deleteCharAt(len - 1).append(RO);
            case VerbInflection.TYPE_GODAN:
                String last = root.substring(len - 1);
                return sb.replace(len - 1, len, getPotentialReplacement(last));
        }
        return sb;
    }

    private String getNegativeReplacement(String s) {
        switch (s) {
            case "う":
                return "わ";
            case "く":
                return "か";
            case "ぐ":
                return "が";
            case "つ":
                return "た";
            case "づ":
                return "だ";
            case "ぬ":
                return "な";
            case "む":
                return "ま";
            case "す":
                return "さ";
            case "ず":
                return "ざ";
            case "る":
                return "ら";
            case "ふ":
                return "は";
            case "ぶ":
                return "ば";
            case "ぷ":
                return "ぱ";
            default:
                return "";
        }
    }

    private String getPoliteReplacement(String s) {
        switch (s) {
            case "う":
                return "い";
            case "く":
                return "き";
            case "ぐ":
                return "ぎ";
            case "つ":
                return "ち";
            case "づ":
                return "ぢ";
            case "ぬ":
                return "に";
            case "む":
                return "み";
            case "す":
                return "し";
            case "ず":
                return "じ";
            case "る":
                return "り";
            case "ふ":
                return "ひ";
            case "ぶ":
                return "び";
            case "ぷ":
                return "ぴ";
            default:
                return "";
        }
    }

    private String getTeStemReplacement(String s) {
        switch (s) {
            case "つ":
            case "る":
            case "く":
            case "ぐ":
                return "っ";
            case "う":
                return "い";
            case "ぶ":
            case "ぬ":
            case "む":
                return "ん";
            case "す":
                return "し";
            default:
                return "";
        }
    }

    private String getPotentialReplacement(String s) {
        switch (s) {
            case "う":
                return "え";
            case "く":
                return "け";
            case "ぐ":
                return "げ";
            case "つ":
                return "て";
            case "づ":
                return "で";
            case "ぬ":
                return "ね";
            case "む":
                return "め";
            case "す":
                return "せ";
            case "ず":
                return "ぜ";
            case "る":
                return "れ";
            case "ふ":
                return "へ";
            case "ぶ":
                return "べ";
            case "ぷ":
                return "ぺ";
            default:
                return "";
        }
    }
}