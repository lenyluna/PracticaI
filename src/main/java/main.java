import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Scanner;
import java.net.UnknownHostException;


/**
 * Created by Leny96 on 27/5/2017.
 */
public class main {

    public static void main(String[] args) throws IOException {
        System.out.println("\n---------------> Practica I <-------------");
        Document doc =null;
        String URL = "";
        while(doc ==null){
           URL= pedirURL();
          doc =  config(URL);
      }
        System.out.println("\n->Parte A: ");
      cantLineas(doc.html());
        System.out.println("\n->Parte B: ");
      cantParrafos(doc);
        System.out.println("\n->Parte C: ");
      cantImg(doc);
        System.out.println("\n->Parte D: ");
      CantFormPortipo(doc);
        System.out.println("\n->Parte E: ");
      campoTipoInput(doc);
        System.out.println("\n->Parte F: ");
        peticion(doc,URL);
    }
    private static String pedirURL(){
        System.out.println("Introduzca una URL");
        String  url = "";
        Scanner lectura = new Scanner(System.in);
        url =lectura.nextLine();
        return url;
    }

    private static Document config(String URL){
        Document doc = null;
        try {
            doc = Jsoup.connect(URL).get();
            //return doc;
        } catch(MalformedURLException error){
            System.out.println("Esta no es una url correcta, falta el protocolo");
        }catch(UnknownHostException eHost){
            System.out.println("Esta no es una url correcta");
        }
        catch (IOException e) {
            System.out.println("Error de entradao salida");
        } catch(IllegalArgumentException ehost){
            System.out.println("Esta no es una url correcta");
        }
        return doc;
    }

    private static void cantLineas(String text){
        String[] lineas = text.split("\n");
        System.out.println("Cantidad de lineas del recurso retornado: " +lineas.length);
       /* for(int i=0; i<lineas.length;i++){
            System.out.println("linea " +i+": "+lineas[i]);
        }*/
    }
    private static void cantParrafos(Document doc){
        Elements ps = doc.getElementsByTag("p");
        System.out.println("Cantidad de parrafos: " +ps.size());
    }
    private static void cantImg(Document doc){
        Elements img = doc.getElementsByTag("p").select("img");
        System.out.println("Cantidad de imagenes dentro de los parrafos: " +img.size());
    }

    private static void CantFormPortipo(Document doc){
        int fpost =0, fget=0;
        Elements forms = doc.getElementsByTag("form");
        for (Element form :forms) {
            if(form.attr("method").equalsIgnoreCase("post")){
                fpost++;
            }else if(form.attr("method").equalsIgnoreCase("get")){
                fget++;
            }
        }
        System.out.println("Cantidad de form con el metodo Post:" +fpost);
        System.out.println("Cantidad de form con el metodo Get:" +fget);
    }
    private static void campoTipoInput(Document doc){
        int i=1,x=1;
        Elements form = doc.getElementsByTag("form");
        for (Element f: form ) {
            System.out.println("Formulario " +x+":");
            for (Element input : f.select("input")) {
                System.out.println("Input "+i+": Tipo: " + input.attr("type"));
                i++;
            }
            if(i==1){
                System.out.println("No hay ninguna etiqueta input");
            }
            System.out.println("");
            i=1;
            x++;
        }
    }
    private static void peticion(Document doc, String URL) throws IOException {
        int x=1;
        Document response = null ;
        Elements form = doc.getElementsByTag("form");
        for (Element f: form ) {
            System.out.println("Formulario " +x+":");
            Elements forms = f.getElementsByAttributeValueContaining("method" , "post");
            for (Element mPost :forms) {
                String  act = mPost.attr("action");
                if(act.charAt(0) == '.') {
                    String sinP = act.substring(1, act.length());
                   response = Jsoup.connect(URL + sinP ).data("Asignatura", "Practica1").post();
                } else {
                    response = Jsoup.connect(URL + act).data("Asignatura", "Practica1").post();
                }

                System.out.println("Respuesta: ");
                System.out.println(response);
            }
            x++;
        }

    }
}
