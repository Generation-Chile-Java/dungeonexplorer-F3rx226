import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class JuegoDeMazmorras {

    //Interfaz para las salas//
    interface Room  {
        void enter(Player player);
    }
    //Interfaz para objetos interactuables//
    interface GameObject {
        void interact(Player player);
    }
    //Clase Player//
    class Player{
        private String name;
        private int health;
        private int attackPower;
        private List<String> inventory;

        public Player(String name, int health, int attackPower) {
            this.name = name;
            this.health = health;
            this.attackPower = attackPower;
            this.inventory = new ArrayList<>();
        }
        public void takeDamage(int damage) {
            this.health -=damage;
            System.out.println(name + "recibió" + damage + " de daño. Vida restante: " + health);
        }

        public void addItem(String item) {
            inventory.add(item);
            System.out.println(name + "recogió: " + item);
        }
        public boolean isAlive(){
            return health > 0;
        }
        public int getAttackPower() {
            return attackPower;
        }
        public void attack(Enemy enemy) {
            System.out.println(name + "ataca al enemigo!");
            enemy.takeDamage(attackPower);
        }
    }
    //Clase Enemy//
    class Enemy {
        private int health;
        private int damage;

        public Enemy(int health, int damage) {
            this.health = health;
            this.damage = damage;
        }

        public void takeDamage(int attackPower) {
            this.health -= attackPower;
            System.out.println("El enemigo recibió " + attackPower + " de daño." + health);
        }

        public boolean isAlive() {
            return health > 0;
        }

        public int getDamage() {
            return 0;
        }
    }
    //Clasw EnemyRoom con sistema de combate//
    class EnemyRoom implements Room {
        private Enemy enemy;

        public EnemyRoom(int health, int damage) {
            this.enemy = new Enemy(health, damage);
        }

        public void enter(Player player) {
            System.out.println("¡UN ENEMIGO APARECE!");
            Scanner scanner = new Scanner(System.in);
            while (player.isAlive() && enemy.isAlive()) {
                System.out.println("Qué quieres hacer? (1) Atacar (2) Huir");
                int choice = scanner.nextInt();
                if (choice == 1) {
                    player.attack(enemy);
                    if (enemy.isAlive()) {
                        System.out.println("El enemigo contraataca!");
                        player.takeDamage(enemy.getDamage());
                    } else {
                        System.out.println("Has derrotado al enemigo!");
                    }
                } else {
                    System.out.println("Has huido de la sala!");
                    break;
                }
            }
        }
    }

    //Clase principal del juego//
    public class DungeonGame {

       private static class EmptyRoom{
           private static class TreasureRoom{

               public TreasureRoom(int i, int i1) {

               }

               public TreasureRoom(String s) {
               }
           }
       }
    }

        public void main(String[] args) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Ingresa tu nombre: ");
            String name = scanner.nextLine();
            Player player = new Player(name, 100, 25);


            Room[] dungeon;
            dungeon = new Room[]{
                    (Room) new DungeonGame.EmptyRoom(),
                    (Room) new DungeonGame.EmptyRoom.TreasureRoom("Espada Mágica."),
                    new EnemyRoom(50, 15),
                    (Room) new DungeonGame.EmptyRoom.TreasureRoom(80, 25)
            };

            for (Room room : dungeon) {
                if (!player.isAlive()) {
                    System.out.println("Game over!");
                    break;
                }
                room.enter(player);
                System.out.println("Presiona Enter para continuar...");
                scanner.nextLine();
            }

            System.out.println("Fin del juego.");
            scanner.nextLine();
        }
    }

