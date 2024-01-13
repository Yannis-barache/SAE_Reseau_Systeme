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

    // // Requêtes concernant les utilisateurs

    public static void ajoutUserJSON(String username, String password, String ip) {
        String path = "./Json/utilisateurs.json";
        JSONArray users = new JSONArray();
        try (Scanner scanner = new Scanner(new File(path))) {
            if (scanner.hasNext()) {
                String content = scanner.useDelimiter("\\Z").next();
                users = new JSONArray(content);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONObject user = new JSONObject();
        user.put("username", username);
        user.put("password", password);
        user.put("ip", ip);
        user.put("followers", new JSONArray());
        user.put("following", new JSONArray());

        users.put(user);

        try (PrintWriter jsonWrite = new PrintWriter(new FileWriter(path))) {
            jsonWrite.write(users.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }


    }    


    public static Set<String> getUserFollowers(String username) {
        String path = "./Json/utilisateurs.json";
        JSONArray users = new JSONArray();
        try (Scanner scanner = new Scanner(new File(path))) {
            if (scanner.hasNext()) {
                String content = scanner.useDelimiter("\\Z").next();
                users = new JSONArray(content);
                for (int i = 0; i < users.length(); i++) {
                    JSONObject user = users.getJSONObject(i);
                    if (user.getString("username").equals(username)) {
                        JSONArray followers = user.getJSONArray("followers");
                        return followers.toList().stream().map(Object::toString).collect(Collectors.toSet());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Set<String> getUserFollowing(String username) {
        String path = "./Json/utilisateurs.json";
        JSONArray users = new JSONArray();
        try (Scanner scanner = new Scanner(new File(path))) {
            if (scanner.hasNext()) {
                String content = scanner.useDelimiter("\\Z").next();
                users = new JSONArray(content);
                for (int i = 0; i < users.length(); i++) {
                    JSONObject user = users.getJSONObject(i);
                    if (user.getString("username").equals(username)) {
                        JSONArray following = user.getJSONArray("followers");
                        return following.toList().stream().map(Object::toString).collect(Collectors.toSet());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean isUserExists(String username) {
        try {
            String content = new String(Files.readAllBytes(Paths.get("./Json/utilisateurs.json")));
            JSONArray jsonArray = new JSONArray(content);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject user = jsonArray.getJSONObject(i);
                if (user.getString("username").equals(username)) {
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
        String path = "./Json/messages.json"; // Ajoutez le chemin vers votre fichier JSON ici

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
        String path = "./Json/messages.json";
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
        String path = "./Json/messages.json";
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
        String path = "./Json/messages.json";
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
            System.out.println("Message avec ID " + id + " n'existe pas.");
            return;
        }

        try {
            PrintWriter jsonWrite = new PrintWriter(new FileWriter(path));
            jsonWrite.write(messages.toString());
            jsonWrite.close(); 
        } catch (Exception e) {
            e.printStackTrace();
        }
    }    

    public static void ajoutFollower(String usernameUtilisateur, String usernameAbonne) {
            String path = "./Json/utilisateurs.json";
            JSONArray users = new JSONArray();
            try (Scanner scanner = new Scanner(new File(path))) {
                if (scanner.hasNext()) {
                    String content = scanner.useDelimiter("\\Z").next();
                    users = new JSONArray(content);
                    for (int i = 0; i < users.length(); i++) {
                        JSONObject user = users.getJSONObject(i);
                        if (user.getString("pseudo").equals(usernameUtilisateur)) {
                            JSONArray followers = user.getJSONArray("abonnements");
                            // Vérifier si l'utilisateur est déjà dans la liste des abonnés
                            if (!followers.toList().contains(usernameAbonne)) {
                                followers.put(usernameAbonne); // Ajouter le pseudo de l'abonné
                                user.put("abonnements", followers); // Mettre à jour la liste des abonnés
                                users.put(i, user); // Remplacer l'utilisateur dans le tableau
                            }
                            break;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
    
            // Réécrire le contenu mis à jour dans le fichier
            try (PrintWriter jsonWrite = new PrintWriter(new FileWriter(path))) {
                jsonWrite.write(users.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        
    }


    public static void deleteFollower(String usernameUtilisateur, String usernameAbonne){
        String path = "./Json/utilisateurs.json";
        JSONArray utilisateurs = new JSONArray();
        try (Scanner scanner = new Scanner(new File(path))) {
            if (scanner.hasNext()) {
                String content = scanner.useDelimiter("\\Z").next();
                utilisateurs = new JSONArray(content);
                for (int i = 0; i < utilisateurs.length(); i++) {
                    JSONObject utilisateur = utilisateurs.getJSONObject(i);
                    if (utilisateur.getString("pseudo").equals(usernameUtilisateur)) {
                        JSONArray abonnes = utilisateur.getJSONArray("abonnees");
                        // Vérifier si l'utilisateur est dans la liste des abonnés
                        if (abonnes.toList().contains(usernameAbonne)) {
                            abonnes.remove(abonnes.toList().indexOf(usernameAbonne)); // Supprimer le pseudo de l'abonné
                            utilisateur.put("abonnees", abonnes); // Mettre à jour la liste des abonnés
                            utilisateurs.put(i, utilisateur); // Remplacer l'utilisateur dans le tableau
                        }
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    
        // Réécrire le contenu mis à jour dans le fichier
        try (PrintWriter jsonWrite = new PrintWriter(new FileWriter(path))) {
            jsonWrite.write(utilisateurs.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}    

    





