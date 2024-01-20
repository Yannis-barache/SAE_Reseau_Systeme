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
    public static User getUser(String username){
        String path = "./Json/utilisateurs.json";
        JSONArray utilisateurs = new JSONArray();
        try{
            Scanner scanner = new Scanner(new File(path));
            if (scanner.hasNext()) {
                String content = scanner.useDelimiter("\\Z").next();
                utilisateurs = new JSONArray(content);
                for (int i = 0; i < utilisateurs.length(); i++) {
                    JSONObject utilisateur = utilisateurs.getJSONObject(i);
                    if (utilisateur.getString("username").equals(username)) {
                        return new User(username, utilisateur.getString("ip"));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        
        }
        return null;

    }





    

    public static void ajoutUserJSON(String username, String ip) {
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

                try (PrintWriter jsonWrite = new PrintWriter(new FileWriter(path))) {
                        jsonWrite.write(messages.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
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

    public static void ajoutFollower(String pseudoUtilisateur, String pseudoAbonne) {
        String path = "./Json/utilisateurs.json";
        JSONArray utilisateurs = new JSONArray();
        try (Scanner scanner = new Scanner(new File(path))) {
            if (scanner.hasNext()) {
                String content = scanner.useDelimiter("\\Z").next();
                utilisateurs = new JSONArray(content);
                for (int i = 0; i < utilisateurs.length(); i++) {
                    JSONObject utilisateur = utilisateurs.getJSONObject(i);
                    if (utilisateur.getString("username").equals(pseudoUtilisateur)) {
                        JSONArray abonnements = utilisateur.getJSONArray("following");
                        if (!abonnements.toList().contains(pseudoAbonne)) {
                            abonnements.put(pseudoAbonne);
                            utilisateur.put("following", abonnements);
                            utilisateurs.put(i, utilisateur);
                        }
                    } else if (utilisateur.getString("username").equals(pseudoAbonne)) {
                        JSONArray abonnes = utilisateur.getJSONArray("followers");
                        if (!abonnes.toList().contains(pseudoUtilisateur)) {
                            abonnes.put(pseudoUtilisateur);
                            utilisateur.put("followers", abonnes);
                            utilisateurs.put(i, utilisateur);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    
        try (PrintWriter jsonWrite = new PrintWriter(new FileWriter(path))) {
            jsonWrite.write(utilisateurs.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void deleteFollower(String pseudoUtilisateur, String pseudoAbonne){
        String path = "./Json/utilisateurs.json";
        JSONArray utilisateurs = new JSONArray();
        try (Scanner scanner = new Scanner(new File(path))) {
            if (scanner.hasNext()) {
                String content = scanner.useDelimiter("\\Z").next();
                utilisateurs = new JSONArray(content);
                for (int i = 0; i < utilisateurs.length(); i++) {
                    JSONObject utilisateur = utilisateurs.getJSONObject(i);
                    if (utilisateur.getString("username").equals(pseudoUtilisateur)) {
                        JSONArray abonnes = utilisateur.getJSONArray("following");
                        // Vérifier si l'utilisateur est dans la liste des abonnés
                        if (abonnes.toList().contains(pseudoAbonne)) {
                            abonnes.remove(abonnes.toList().indexOf(pseudoAbonne)); // Supprimer le pseudo de l'abonné
                            utilisateur.put("following", abonnes); // Mettre à jour la liste des abonnés
                            utilisateurs.put(i, utilisateur); // Remplacer l'utilisateur dans le tableau
                        }
                    } else if (utilisateur.getString("username").equals(pseudoAbonne)) {
                        JSONArray abonnes = utilisateur.getJSONArray("followers");
                        // Vérifier si l'utilisateur est dans la liste des abonnés
                        if (abonnes.toList().contains(pseudoUtilisateur)) {
                            abonnes.remove(abonnes.toList().indexOf(pseudoUtilisateur)); // Supprimer le pseudo de l'utilisateur
                            utilisateur.put("followers", abonnes); // Mettre à jour la liste des abonnés
                            utilisateurs.put(i, utilisateur); // Remplacer l'utilisateur dans le tableau
                        }
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

    





