package br.hendrew.movidesk.repository;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import br.hendrew.movidesk.entity.Tickets;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;

@ApplicationScoped
public class TicketsRepository implements PanacheRepository<Tickets> {

    public List<Tickets> findBybaseStatus(String basestatus) {
        return find("basestatus", basestatus).list();
    }

    public List<Tickets> findByUrgency(String urgency) {
        return find("(basestatus = 'InAttendance' or basestatus = 'New' "
                + "or basestatus = 'Stopped') and urgency = (:urgency)", Parameters.with("urgency", urgency)).list();
    }

    public List<Tickets> findByUrgencyIsNull(String urgency) {
        return find("(basestatus = 'InAttendance' or basestatus = (:urgency) "
                + "or basestatus = 'Stopped') and urgency is null", Parameters.with("urgency", urgency)).list();
    }

    public List<Tickets> findBytype(long type) {
        return find("(basestatus = 'InAttendance' or basestatus = 'New' "
                + "or basestatus = 'Stopped') and type = (:type)", Parameters.with("type", type)).list();
    }

    public List<Tickets> findByAbertos(String abertos) {
        return find("(basestatus = 'InAttendance' or basestatus = (:abertos) "
                + "or basestatus = 'Stopped')", Parameters.with("abertos", abertos)).list();
    }

    public List<Tickets> findByCategory(String categoria) {
        return find("(basestatus = 'InAttendance' or basestatus = 'New' "
                + "or basestatus = 'Stopped') and category =(:categoria)", Parameters.with("categoria", categoria))
                        .list();
    }

    public List<Tickets> findBySemCategoria(String semcategoria) {
        return find("(basestatus = 'InAttendance' or basestatus = (:semcategoria) "
                + "or basestatus = 'Stopped') and category is null", Parameters.with("semcategoria", semcategoria))
                        .list();
    }

    public List<Tickets> findByJustification(String justification){
        return find("(basestatus = 'InAttendance' or basestatus = 'New' "
                + "or basestatus = 'Stopped') and justification = (:justification)", Parameters.with("justification", justification))
                        .list();
    }

    public List<Tickets> findBySemJustification(String semjustification){
        return find("(basestatus = 'InAttendance' or basestatus = (:semjustification) "
                + "or basestatus = 'Stopped') and justification is null", Parameters.with("semjustification", semjustification))
                        .list();
    }

    

}
