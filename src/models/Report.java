package models;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Table(name = "reports")
@NamedQueries({
    @NamedQuery(
            name = "getAllReports",
            query = "SELECT r FROM Report AS r ORDER BY r.id DESC"
    ),
    @NamedQuery(
            name = "getReportsCount",
            query = "SELECT COUNT(r) FROM Report AS r"
    ),
    //ログインしているEmployeeが作成した日報を、日報idの降順で全件取得
    @NamedQuery(
            name = "getMyAllReports",
            query = "SELECT r FROM Report AS r WHERE r.employee = :employee ORDER BY r.id DESC"
    ),
    //ログインしているEmployeeが作成した日報を集計する
    @NamedQuery(
            name = "getMyReportsCount",
            query = "SELECT COUNT(r) FROM Report AS r WHERE r.employee = :employee"
    )

})
@Entity
public class Report {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne //多対一、Reportから見たらEmployeeは一つ
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Column(name = "report_date", nullable = false)
    private Date report_date;

    @Column(name = "title", length = 255, nullable = false)
    private String title;

    @Lob //改行も保存される
    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "created_at", nullable = false)
    private Timestamp created_at;

    @Column(name = "updated_at", nullable = false)
    private Timestamp updated_at;

    @ManyToMany
    @JoinTable(
            name = "join_table", //中間テーブルの名前を決める
            joinColumns = { //Joinするカラムの主キーを書く
              @JoinColumn(name = "Report_id", referencedColumnName = "id", unique=true) //このクラスの分
            },
            inverseJoinColumns = {
              @JoinColumn(name = "Employee_id", referencedColumnName = "id") // 相手クラスの分
            }
          )
          private List<Employee> employees_who_liked_report; //このクラスで使う、相手クラスが入ったリスト

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Date getReport_date() {
        return report_date;
    }

    public void setReport_date(Date report_date) {
        this.report_date = report_date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }

    public List<Employee> getEmployees_who_liked_report() {
        return employees_who_liked_report;
    }

    public void setEmployees_who_liked_report(List<Employee> employees_who_liked_report) {
        this.employees_who_liked_report = employees_who_liked_report;
    }
}
