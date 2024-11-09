package m2tienda.m2tienda.entities;

public class Category {
    private Integer id;
    private String name;
    private Integer order;

//    public Category(Integer id, String name, String description, Integer order) {
//        this.id = id;
//        this.name = name;
//        this.description = description;
//        this.order = order;
//    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", order=" + order +
                '}';
    }
}
