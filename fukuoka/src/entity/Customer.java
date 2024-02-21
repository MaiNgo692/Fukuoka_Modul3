package entity;



import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;

import javax.persistence.Table;

@Table(name = "users")
public class Customer {
    private int id;
    private  String name;
    private String email;
    private int age;
    private String address;
    private boolean delFlag;
}
