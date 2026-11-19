package org.example;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static List<Product> findProductByName(String name , List<Product> products){
        List<Product> result = new ArrayList<>();
        for(Product product : products ) {
            if(product.getName().contains(name)) result.add(product);
        }
        return result;
    }
    public static Product findProductById(String id , List<Product> products){
        Map<String , Product> mapProducts = new HashMap<>();
        products.forEach(p -> {
            mapProducts.put(p.getId(), p);
        });
        return mapProducts.get(id);
    }

    public static List<Product> filterProductByMaterial(String materialId, List<Product> products){
        List<Product> result = new ArrayList<>();
        for(Product product : products ) {
            if(Objects.equals(product.getMaterialId(), materialId)) result.add(product);
        }
        return result;
    }
    public static void sortProductByName(List<Product> products){
        Collections.sort(products);
    }
    public static Map<String, List<Product>> groupProduct(
            List<Product> products) {

        Map<String, List<Product>> result = new HashMap<>();

        products.forEach(p ->
                result.computeIfAbsent(
                        p.getMaterialId(),
                        key -> new ArrayList<>()
                ).add(p)
        );

        return result;
    }
    public static void main(String[] args) {
        List<Material> materials = new ArrayList<>();
        List<Product> products = new ArrayList<>();

        materials.add(new Material("0","cao su"));
        materials.add(new Material("1","sat"));

        products.add(new Product("0","dep","0"));
        products.add(new Product("3","tui","1"));
        products.add(new Product("1","ao","0"));
        products.add(new Product("2","mu","1"));

//      System.out.println(Arrays.toString(products.toArray()));
      System.out.println(Arrays.toString(groupProduct(products).values().toArray()));
//      System.out.println(filterProductByMaterial("1",products));


    }

}

class Product implements Comparable<Product>{
    private String id;
    private String name;
    private String materialId;

    public Product(String id, String name, String materialId) {
        this.id = id;
        this.name = name;
        this.materialId = materialId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMaterialId() {
        return materialId;
    }

    public void setMaterialId(String materialId) {
        this.materialId = materialId;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", materialId='" + materialId + '\'' +
                '}';
    }

    @Override
    public int compareTo(Product o) {
        return this.name.compareTo(o.name);
    }
}
class Material{
    private String id;
    private String name;

    public Material(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}