package codeFiles; /**
 * Asvin and Alex
 * Due date: Jan 22
 * This is the comparator
 */

import java.util.Comparator;

public class randomizeQuestionsComparator implements Comparator<String> {

    @Override
    // this randomly sorts strings
    public int compare(String o1, String o2) {
        double chance = Math.random()*(2-1+1) + 1;
        if(chance == 1) return 1;
        return -1;
    }
}
