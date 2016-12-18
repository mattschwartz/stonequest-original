/* *****************************************************************************
 *   Project:        StoneQuest
 *   File name:      LineElement.java
 *   Author:         Matt Schwartz
 *   Date:           12.14.2013
 *   License:        You are free to use, reuse, and edit any of the text in
 *                   this file.  You are not allowed to take credit for code
 *                   that was not written fully by yourself, or to remove 
 *                   credit from code that was not written fully by yourself.  
 *                   Please email mattschwartz@utexas.edu for issues or concerns.
 *   Description:    
 **************************************************************************** */

package com.barelyconscious.util;

import java.util.ArrayList;
import java.util.List;

public class LineElement {
    public List<CharacterElement> line = new ArrayList<CharacterElement>();
    
    public LineElement() {
    }
    
    public LineElement(String msg) {
        addString(msg, TextLogHelper.TEXTLOG_DEFAULT_COLOR);
    }
    
    public void add(CharacterElement ce) {
        line.add(ce);
    }
    
    public void add(char c, int col) {
        line.add(new CharacterElement(c, col));
    }
    
    public LineElement substring(int start, int end) {
        LineElement subLine = new LineElement();
        
        if (start < 0 || end < 0 || start > line.size() || end > line.size()) {
            return null;
        }
        
        for (int i = start; i < end; i++) {
            subLine.add(line.get(i));
        }
        
        return subLine;
    }
    
    public List<LineElement> split(int cols) {
        List<LineElement> broken = new ArrayList<LineElement>();
        boolean endOfString = false;
        int start = 0;
        int end;
        while (start < line.size()) {
            int charCount = 0;
            int lastSpace = 0;
            while (charCount < cols) {
                if (line.get(charCount + start).data == '\n') {
                    lastSpace = charCount;
                    break;
                } else if (line.get(charCount + start).data == ' ') {
                    lastSpace = charCount;
                }
                charCount++;
                if (charCount + start == line.size()) {
                    endOfString = true;
                    break;
                }
            }
            end = endOfString ? line.size() : (lastSpace > 0) ? lastSpace + start : charCount + start;
            broken.add(substring(start, end));
            start = end + 1;
        }
        return broken;
    }
    
    public final void addString(String msg, int col) {
        for (char c : msg.toCharArray()) {
            line.add(new CharacterElement(c, col));
        }
    }
    
    public static LineElement parseString(String msg, String match, int baseColor, int matchedTextColor) {
        return parseString(msg, new String[]{match}, baseColor, new int[]{matchedTextColor});
    }
    
    public static LineElement parseString(String msg, String[] match, int baseColor, int[] matchedTextColor) {
        char c;
        int id = 127;
        LineElement line = new LineElement();
        
        for (String str : match) {
            msg = msg.replaceAll(str, "" + (char)++id);            
        }
        
        for (int i = 0; i < msg.length(); i++) {
            c = msg.charAt(i);
            if (c > (char)127) {                
                for (char c2 : match[c-128].toCharArray()) {
                    line.add(c2, matchedTextColor[c-128]);
                }
            }
            else {
                line.add(c, baseColor);
            }
        }
        
        return line;
    }

    @Override
    public String toString() {
        String str = "";
        
        for (CharacterElement c : line) {
            str += c;
        }
        
        return str;
    }
}
