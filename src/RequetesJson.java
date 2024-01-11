import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RequetesJson {

    // Requêtes concernant les utilisateurs

    public static void ajoutUtilisateurJSON(String pseudo, String ip) {
        String path = "./json/utilisateurs.json";
        JSONArray utilisateurs = new JSONArray();
        try (Scanner scanner = new Scanner(new File(path))) {
            if (scanner.hasNext()) {
                String content = scanner.useDelimiter("\\Z").next();
                utilisateurs = new JSONArray(content);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        JSONObject utilisateur = new JSONObject();
        utilisateur.put("pseudo", pseudo);
        utilisateur.put("ip", ip);
        utilisateur.put("abonnees", new JSONArray());
        utilisateur.put("abonnements", new JSONArray());

        utilisateurs.put(utilisateur);

        try (PrintWriter jsonWrite = new PrintWriter(new FileWriter(path))) {
            jsonWrite.write(utilisateurs.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Set<String> getAbonnesUtilisateur(String pseudo) {
        String path = "./json/utilisateurs.json";
        JSONArray utilisateurs = new JSONArray();
        try (Scanner scanner = new Scanner(new File(path))) {
            if (scanner.hasNext()) {
                String content = scanner.useDelimiter("\\Z").next();
                utilisateurs = new JSONArray(content);
                for (int i = 0; i < utilisateurs.length(); i++) {
                    JSONObject utilisateur = utilisateurs.getJSONObject(i);
                    if (utilisateur.getString("pseudo").equals(pseudo)) {
                        JSONArray abonnees = utilisateur.getJSONArray("abonnees");
                        return abonnees.toList().stream().map(Object::toString).collect(Collectors.toSet());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Set<String> getAbonnementsUtilisateurs(String pseudo) {
        String path = "./json/utilisateurs.json";
        JSONArray utilisateurs = new JSONArray();
        try (Scanner scanner = new Scanner(new File(path))) {
            if (scanner.hasNext()) {
                String content = scanner.useDelimiter("\\Z").next();
                utilisateurs = new JSONArray(content);
                for (int i = 0; i < utilisateurs.length(); i++) {
                    JSONObject utilisateur = utilisateurs.getJSONObject(i);
                    if (utilisateur.getString("pseudo").equals(pseudo)) {
                        JSONArray abonnements = utilisateur.getJSONArray("abonnements");
                        return abonnements.toList().stream().map(Object::toString).collect(Collectors.toSet());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean isUserExists(String pseudo) {
        try {
            String content = new String(Files.readAllBytes(Paths.get("./json/utilisateurs.json")));
            JSONArray jsonArray = new JSONArray(content);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject user = jsonArray.getJSONObject(i);
                if (user.getString("pseudo").equals(pseudo)) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Requêtes concernant les messages

    public static int ajoutMessageJSON(String contenu, String nomAuteur, int nbLikes, LocalDate date) {
        String path = "./json/messages.json"; // Ajoutez le chemin vers votre fichier JSON ici

        // Lire le contenu existant du fichier et trouver l'ID le plus élevé
        int maxId = 0;
        JSONArray messages = new JSONArray();
        try (Scanner scanner = new Scanner(new File(path))) {
            if (scanner.hasNext()) {
                String content = scanner.useDelimiter("\\Z").next();
                messages = new JSONArray(content);
                for (int i = 0; i < messages.length(); i++) {
                    JSONObject message = messages.getJSONObject(i);
                    if (message.getInt("id") > maxId) {
                        maxId = message.getInt("id");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        JSONObject newMessage = new JSONObject();
        try {
            newMessage.put("id", maxId + 1);
            newMessage.put("user", nomAuteur);
            newMessage.put("message", contenu);
            newMessage.put("date", date.toString());
            newMessage.put("likes", nbLikes);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Ajouter le nouveau message
        messages.put(newMessage);

        // Réécrire le contenu mis à jour dans le fichier
        try (PrintWriter jsonWrite = new PrintWriter(new FileWriter(path))) {
            jsonWrite.write(messages.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return maxId + 1;
    }

    public static String[] rechercheMessageParId(int id) {
        String path = "./json/messages.json";
        String[] result = new String[4];

        // Lire le contenu existant du fichier et trouver le message avec l'ID donné
        JSONArray messages = new JSONArray();
        try (Scanner scanner = new Scanner(new File(path))) {
            if (scanner.hasNext()) {
                String content = scanner.useDelimiter("\\Z").next();
                messages = new JSONArray(content);
                for (int i = 0; i < messages.length(); i++) {
                    JSONObject message = messages.getJSONObject(i);
                    if (message.getInt("id") == id) {
                        result[0] = message.getString("message");
                        result[1] = message.getString("user");
                        result[2] = String.valueOf(message.getInt("likes"));
                        result[3] = message.getString("date");
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static void likeMessage(int id) {
        String path = "./json/messages.json";
        JSONArray messages = new JSONArray();
        try (Scanner scanner = new Scanner(new File(path))) {
            if (scanner.hasNext()) {
                String content = scanner.useDelimiter("\\Z").next();
                messages = new JSONArray(content);
                for (int i = 0; i < messages.length(); i++) {
                    JSONObject message = messages.getJSONObject(i);
                    if (message.getInt("id") == id) {
                        int likes = message.getInt("likes");
                        message.put("likes", likes + 1);
                        messages.put(i, message); // Remplacez l'objet dans le tableau
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Réécrire le contenu mis à jour dans le fichier
        try (PrintWriter jsonWrite = new PrintWriter(new FileWriter(path))) {
            jsonWrite.write(messages.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteMessage(int id) {
        String path = "./json/messages.json";
        JSONArray messages = new JSONArray();
        boolean idExists = false;
        try (Scanner scanner = new Scanner(new File(path))) {
            if (scanner.hasNext()) {
                String content = scanner.useDelimiter("\\Z").next();
                messages = new JSONArray(content);
                Iterator<Object> it = messages.iterator();
                while (it.hasNext()) {
                    JSONObject message = (JSONObject) it.next();
                    if (message.getInt("id") == id) { // Utiliser l'ID de la classe
                        idExists = true;
                        it.remove(); // Supprimer le message
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!idExists) {
            System.out.println("Message with ID " + id + " does not exist.");
            return;
        }

        try {
            PrintWriter jsonWrite = new PrintWriter(new FileWriter(path));
            jsonWrite.write(messages.toString());
            jsonWrite.close(); // Fermer explicitement le PrintWriter
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
