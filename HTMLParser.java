package com.jerrosHaven;

/*An overcomplicated HTML parser I've written using regular expressions.
It prints contents enclosed by tags (the most nested first) from a string 
provided by the way of standard input. I later realized I didn't need regex 
at all but this is what I came up with originally.*/

import java.util.ArrayDeque;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HTMLParser {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String text = sc.nextLine();

        ArrayDeque<String> tagStack = new ArrayDeque<>();

        Pattern tagPattern = Pattern.compile("<([^/]+?)>");
        Matcher tagMatcher = tagPattern.matcher(text);
        Pattern closeTagPattern = Pattern.compile("</(.+?)>");
        Matcher closeTagMatcher = closeTagPattern.matcher(text);
        String tag;
        String[] tagAndIndexArray;
        int tagsIndex;
        int closingTagSearchIndex = 0;

        while (tagMatcher.find()) {
            tagStack.push(tagMatcher.group(1) + " " + tagMatcher.end());

            while (closeTagMatcher.find(closingTagSearchIndex) && !tagStack.isEmpty()) {
                tagAndIndexArray = tagStack.peek().split(" ");
                tag = tagAndIndexArray[0];
                tagsIndex = Integer.parseInt(tagAndIndexArray[1]);
                if (closeTagMatcher.group(1).equals(tag)) {
                    System.out.println(text.substring(tagsIndex, closeTagMatcher.start()));
                    tagStack.pop();
                    closingTagSearchIndex = closeTagMatcher.end();
                } else {
                    break;
                }
            }
        }
    }
}
