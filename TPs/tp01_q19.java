import java.util.Scanner;

class tp01_q05 {

    static boolean[] Boolrecursivo(String s, int pos, int n, boolean[] arr, int cnt) {
        if (cnt == n || pos >= s.length())
            return arr;
        char ch = s.charAt(pos);
        if (ch == '1' || ch == '0') {
            arr[cnt] = (ch == '1');
            return Boolrecursivo(s, pos + 1, n, arr, cnt + 1);
        }
        return Boolrecursivo(s, pos + 1, n, arr, cnt);
    }

    static boolean[] get(String s) {
        int n = s.charAt(0) - '0';
        boolean[] arr = new boolean[n];
        return Boolrecursivo(s, 0, n, arr, 0);
    }

    static String replaceOps(String s, int i, String acc) {
        if (i >= s.length())
            return acc;
        if (i <= s.length() - 3 && s.charAt(i) == 'a' && s.charAt(i + 1) == 'n' && s.charAt(i + 2) == 'd')
            return replaceOps(s, i + 3, acc + "^");
        if (i <= s.length() - 2 && s.charAt(i) == 'o' && s.charAt(i + 1) == 'r')
            return replaceOps(s, i + 2, acc + "V");
        if (i <= s.length() - 3 && s.charAt(i) == 'n' && s.charAt(i + 1) == 'o' && s.charAt(i + 2) == 't')
            return replaceOps(s, i + 3, acc + "!");
        return replaceOps(s, i + 1, acc + s.charAt(i));
    }

    static String variavel(String s, int i, String acc, boolean[] vals) {
        if (i >= s.length())
            return acc;
        char ch = s.charAt(i);
        if (ch >= 'A' && ch <= 'C') {
            int idx = ch - 'A';
            return variavel(s, i + 1, acc + (vals[idx] ? '1' : '0'), vals);
        }
        return variavel(s, i + 1, acc + ch, vals);
    }

    static String limpar(String s, int i, boolean found, String acc) {
        if (i >= s.length())
            return acc;
        char ch = s.charAt(i);
        if (!found && (ch == '^' || ch == 'V' || ch == '!'))
            found = true;
        if (found && ch != ' ' && ch != ',')
            acc += ch;
        return limpar(s, i + 1, found, acc);
    }

    static String limparExpressao(String s) {
        return limpar(s, 0, false, "");
    }

    static String subOperators(String s, boolean[] vals) {
        String t = replaceOps(s, 0, "");
        String t2 = variavel(t, 0, "", vals);
        return limparExpressao(t2);
    }

    static int findOp(String s, int i) {
        if (i < 0)
            return -1;
        char ch = s.charAt(i);
        if (ch == '^' || ch == 'V' || ch == '!')
            return i;
        return findOp(s, i - 1);
    }

    static int findClose(String s, int i) {
        if (i >= s.length())
            return s.length() - 1;
        if (s.charAt(i) == ')')
            return i;
        return findClose(s, i + 1);
    }

    static int[] countRec(String s, int i, int end, int tot, int ones) {
        if (i > end)
            return new int[] { tot, ones };
        char ch = s.charAt(i);
        if (ch == '0' || ch == '1') {
            tot++;
            if (ch == '1')
                ones++;
        }
        return countRec(s, i + 1, end, tot, ones);
    }

    static String applyAnd(String s, int pos) {
        int end = findClose(s, pos);
        int[] cnt = countRec(s, pos, end, 0, 0);
        char res = (cnt[1] == cnt[0]) ? '1' : '0';
        return s.substring(0, pos) + res + s.substring(end + 1);
    }

    static String applyOr(String s, int pos) {
        int end = findClose(s, pos);
        int[] cnt = countRec(s, pos, end, 0, 0);
        char res = (cnt[1] > 0) ? '1' : '0';
        return s.substring(0, pos) + res + s.substring(end + 1);
    }

    static String applyNot(String s, int pos) {
        int i = pos;
        while (i < s.length() && s.charAt(i) != ')') {
            if (s.charAt(i) == '0' || s.charAt(i) == '1') {
                char res = (s.charAt(i) == '1') ? '0' : '1';
                return s.substring(0, pos) + res + s.substring(i + 2);
            }
            i++;
        }
        return s;
    }

    static String evalExp(String s) {
        int pos = findOp(s, s.length() - 1);
        if (pos == -1)
            return s;
        char op = s.charAt(pos);
        if (op == '^')
            s = applyAnd(s, pos);
        else if (op == 'V')
            s = applyOr(s, pos);
        else if (op == '!')
            s = applyNot(s, pos);
        return evalExp(s);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            String in = sc.nextLine();
            if (in.equals("0"))
                break;
            boolean[] vals = get(in);
            String exp = subOperators(in, vals);
            String res = evalExp(exp);
            System.out.println(res);
        }
        sc.close();
    }
}
