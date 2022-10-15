import java.io.IOException; //(1. )
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

class SearchEngine{
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}

class Handler implements URLHandler {
    String str;
    List<String> strs = new ArrayList<String>();


    @Override
    public String handleRequest(URI url) { //(2. )
        if (url.getPath().contains("/add")) {
            String[] parameters = url.getQuery().split("=");
            this.str = parameters[1];
            this.strs.add(this.str);
            if (parameters[0].equals("s")) {
                return this.str + " has been added!";
            }
            return "404 Not Found";
        } 
        else {
            System.out.println("Path: " + url.getPath());
            if (url.getPath().contains("/search")) {
                String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("s")) {
                    this.str = parameters[1];
                    List<String> goodStrs = new ArrayList<String>();
                    for(String s: this.strs) {
                        if(s.indexOf(this.str) != -1) {
                            goodStrs.add(s);
                        } 
                    }
                    String result = "";
                    for(int i = 0; i < goodStrs.size(); i++) {
                        result += goodStrs.get(i) + ", ";
                    }
                    return result;
                }
                return "/search not successful";
            }
            return "HomePage";
        }
    }
}