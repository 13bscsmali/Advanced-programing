/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interpreter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


/**
 *
 * @author mali.bscs13seecs
 */
public class Interpreter {

    /**
     * @param args the command line arguments
     */
     static ArrayList<String> statements = new ArrayList<String>();
     static HashMap<String, Integer> variables = new HashMap<String, Integer>();
     public static boolean isInteger(String str) {
    if (str == null) {
        return false;
    }
    int length = str.length();
    if (length == 0) {
        return false;
    }
    int i = 0;
    if (str.charAt(0) == '-') {
        if (length == 1) {
            return false;
        }
        i = 1;
    }
    for (; i < length; i++) {
        char c = str.charAt(i);
        if (c < '0' || c > '9') {
            return false;
        }
    }
    return true;
}
    static boolean IsLet(String key)
    {
        if(key == "Let" || key == "let")
            return true;
        
        return false;
    }
    static boolean exist(String key)
    {
        if(variables.containsKey(key))
            return true;
        
        return false;
    }
    static boolean IsPrint(String key)
    {
        if(key == "Print" || key == "print")
            return true;
        
        return false;
    }
    static boolean PEqu_analyzer (String[] words)
    {
        if(words[2] == "+" ||words[2] == "-" ||words[2] == "*" ||words[2] == "/" )
        {
            if(words.length> 3)
            {
                if(exist(words[3]))
                    return true;
                else
                {
                    if(isInteger(words[3]))
                        return true;
                    else
                    return false; // the variable doesnot exist
                }
            }
            else
                return false; // there should be a variable or a number
        }
        else 
            return false; // no such operation exists
    }
    static boolean Print_analyzer (String[] words)
    {
        if(words.length>1)
        {
            if(!IsLet(words[1]) && !IsPrint(words[1]))
            {
                if(exist(words[1]))
                {
                    if(words.length>2)
                    {
                       if(PEqu_analyzer(words)) 
                           return true;
                       else 
                           return false;
                    }
                    else
                        return true;
                }
                else
                    return false; /// there is no variable of that name
            }
            else
                return false; /// no keywords print or let allowed after print
            
        }
        else
            return false; /// print word alone is not enough
        
    }
    static boolean Let_analyzer (String[] words)
    {
        if(!IsLet(words[1]) && !IsPrint(words[1]))
        { if(!exist(words[1]))
            {if(words.length>2)
                { if(words[2] == "=")
                    { if(words.length>3)
                        {if(isInteger(words[3]))
                                return true;
                        else
                        {
                            if(exist(words[3]))
                                    return true;
                            else 
                                return false;// the variable assigned does not exsist
                                      
                            }
                        }
                        else 
                            return false;}// nothing after =  
                    else 
                        return false;}// expected = but somthing else given
                else 
                    return true;} // like let y
            else 
                return false;} // this variable is already defined
        else
            return false; // expected a variable name not a key word
        
 
    }
    static boolean StatementAnalyzer (String stat)
    {
        String[] word = stat.split(" ");
        
        if(IsLet(word[0]))
           if(Let_analyzer(word)) ////// checking let statement
               return true;
        else
           {
               if(IsPrint(word[0]))
                   if(Print_analyzer(word)) //// checking print statement
                       return true;
               
           }
        return false;
    }
    public static void main(String[] args) {
        // TODO code application logic here
          BufferedReader myfile=null;
        try {
			String sCurrentLine;

			myfile = new BufferedReader(new FileReader("C:\\Users\\mali.bscs13seecs\\Desktop\\code.txt"));

			while ((sCurrentLine = myfile.readLine()) != null) {
			statements.add(sCurrentLine);	
                         System.out.println(sCurrentLine);
                         
                         
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
      
      
    }
    }
    

