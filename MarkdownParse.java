// File reading code from https://howtodoinjava.com/java/io/java-read-file-to-string-examples/
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class MarkdownParse {
    static public boolean isEscaped(int currentIndex, String markdown){
        return currentIndex != 0 && markdown.charAt(currentIndex-1) == '\\';
    }
    static public int advance(int index, String markdown){
        while(isEscaped(index, markdown)){
            index += 2;
        }
        return index;
    }
    public static ArrayList<String> getLinks(String markdown) {
        ArrayList<String> toReturn = new ArrayList<>();
        // find the next [, then find the ], then find the (, then take up to
        // the next )
        int currentIndex = 0;
        while(currentIndex < markdown.length()) {
            int nextOpenBracket = 0;
            int nextCloseBracket = 0;
            int openParen = 0;
            int closeParen = 0;
            int i;
            currentIndex = advance(currentIndex, markdown);

            nextOpenBracket = markdown.indexOf("[", currentIndex);
            i = advance(nextOpenBracket, markdown);

            nextCloseBracket = markdown.indexOf("]", i);

            i = advance(nextCloseBracket, markdown);
            openParen = markdown.indexOf("(", i);

            i = advance(openParen, markdown);
            closeParen = markdown.indexOf(")", i);

            toReturn.add(markdown.substring(openParen + 1, closeParen));
            currentIndex = closeParen + 1;
            if(markdown.indexOf("[", currentIndex) == -1){
                break;
            }
        }
        return toReturn;
    }
    public static void main(String[] args) throws IOException {
		Path fileName = Path.of(args[0]);
	    String contents = Files.readString(fileName);
        ArrayList<String> links = getLinks(contents);
        System.out.println(links);
    }
}