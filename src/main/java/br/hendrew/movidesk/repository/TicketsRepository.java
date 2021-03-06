package br.hendrew.movidesk.repository;

import java.sql.Time;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import br.hendrew.movidesk.entity.CategoryOwner;
import br.hendrew.movidesk.entity.Tickets;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;
import io.quarkus.panache.common.Sort;

@ApplicationScoped
public class TicketsRepository implements PanacheRepository<Tickets> {

        public List<Tickets> findBybaseStatus(String basestatus) {
                return find("basestatus", Sort.ascending("id"), basestatus).list();
        }

        public List<Tickets> findBybaseStatusDate(String basestatus, Date dateInicio, Date dateFim,
                        List<CategoryOwner> categoryOwner) {
                return find("select t from Tickets t left join t.owner o where " +
                                "basestatus = :basestatus and dataticket between :dateInicio and :dateFim " +
                                "and categoryOwner in (:categoryOwner)",
                                Parameters.with("basestatus", basestatus)
                                                .and("dateInicio", dateInicio).and("dateFim", dateFim)
                                                .and("categoryOwner", categoryOwner)).list();
        }

        public List<Tickets> findByUrgency(String urgency) {
                return find("(basestatus = 'InAttendance' or basestatus = 'New' "
                                + "or basestatus = 'Stopped') and urgency = (:urgency)",
                                Parameters.with("urgency", urgency)).list();
        }

        public List<Tickets> findByUrgencyDate(String urgency, Date dateInicio, Date dateFim,
                        List<CategoryOwner> categoryOwner) {
                return find("select t from Tickets t left join t.owner o where " +
                                "urgency = :urgency and dataticket between :dateInicio and :dateFim " +
                                "and categoryOwner in (:categoryOwner)",
                                Parameters.with("urgency", urgency)
                                                .and("dateInicio", dateInicio).and("dateFim", dateFim)
                                                .and("categoryOwner", categoryOwner)).list();

        }

        public List<Tickets> findByUrgencyIsNull(String urgency) {
                return find("(basestatus = 'InAttendance' or basestatus = (:urgency) "
                                + "or basestatus = 'Stopped') and urgency is null", Parameters.with("urgency", urgency))
                                                .list();
        }

        public List<Tickets> findByUrgencyIsNullDate(Date dateInicio, Date dateFim, List<CategoryOwner> categoryOwner) {
                return find("select t from Tickets t left join t.owner o where " +
                                "urgency is null and dataticket between :dateInicio and :dateFim " +
                                "and categoryOwner in (:categoryOwner)",
                                Parameters.with("dateInicio", dateInicio).and("dateFim", dateFim).and("categoryOwner",
                                                categoryOwner)).list();
        }

        public List<Tickets> findBytype(long type) {
                return find("(basestatus = 'InAttendance' or basestatus = 'New' "
                                + "or basestatus = 'Stopped') and type = (:type)", Parameters.with("type", type))
                                                .list();
        }

        public List<Tickets> findByAbertos(String abertos) {
                return find("(basestatus = 'InAttendance' or basestatus = (:abertos) "
                                + "or basestatus = 'Stopped')", Parameters.with("abertos", abertos)).list();
        }

        public List<Tickets> findByCategory(String categoria) {
                return find("(basestatus = 'InAttendance' or basestatus = 'New' "
                                + "or basestatus = 'Stopped') and category =(:categoria)",
                                Parameters.with("categoria", categoria))
                                                .list();
        }

        public List<Tickets> findByCategoryDate(String categoria, Date dateInicio, Date dateFim,
                        List<CategoryOwner> categoryOwner) {
                return find("select t from Tickets t left join t.owner o where " +
                                "category =:categoria and dataticket between :dateInicio and :dateFim " +
                                "and categoryOwner in (:categoryOwner)",
                                Parameters.with("categoria", categoria).and("dateInicio", dateInicio)
                                                .and("dateFim", dateFim).and("categoryOwner", categoryOwner)).list();
        }

        public List<Tickets> findBySemCategoryDate(Date dateInicio, Date dateFim, List<CategoryOwner> categoryOwner) {
                return find("select t from Tickets t left join t.owner o where " +
                                "category is null and dataticket between :dateInicio and :dateFim " +
                                "and categoryOwner in (:categoryOwner)",
                                Parameters.with("dateInicio", dateInicio).and("dateFim", dateFim)
                                                .and("categoryOwner", categoryOwner)).list();
        }

        public List<Tickets> findBySemCategoria(String semcategoria) {
                return find("(basestatus = 'InAttendance' or basestatus = (:semcategoria) "
                                + "or basestatus = 'Stopped') and category is null",
                                Parameters.with("semcategoria", semcategoria))
                                                .list();
        }

        public List<Tickets> findByJustification(String justification) {
                return find("(basestatus = 'InAttendance' or basestatus = 'New' "
                                + "or basestatus = 'Stopped') and justification = (:justification)",
                                Parameters.with("justification", justification))
                                                .list();
        }

        public List<Tickets> findBySemJustification(String semjustification) {
                return find("(basestatus = 'InAttendance' or basestatus = (:semjustification) "
                                + "or basestatus = 'Stopped') and justification is null",
                                Parameters.with("semjustification", semjustification))
                                                .list();
        }

        public long findByTicketsDateCount(Date dateInicio, Date dateFim) {
                return find("dataticket between (:dateinicio) and (:datefim)",
                                Parameters.with("dateinicio", dateInicio).and("datefim", dateFim)).count();
        }

        public List<Tickets> findByTicketsDate(Date dateInicio, Date dateFim,List<CategoryOwner> categoryowner) {
                return find("select t from Tickets t left join t.owner o where " +
                                "dataticket between :dateinicio and :datefim " +
                                "and categoryOwner in (:categoryOwner)",
                                Parameters.with("dateinicio", dateInicio).and("datefim", dateFim).and("categoryOwner", categoryowner)).list();
        }

        public List<Tickets> findByTicketsDateTime(Date dateInicio, Date dateFim, Time horaInicial, Time HoraFinal,List<CategoryOwner> categoryOwner) {
                        return find("select t from Tickets t left join t.owner o where " +
                                        "dataticket between :dateinicio and :datefim and "+
                                        "horaticket between :horainicial and :horafinal "+
                                        "and categoryOwner in (:categoryOwner)",
                                        Parameters.with("dateinicio", dateInicio)
                                                .and("datefim", dateFim)
                                                .and("horainicial", horaInicial)
                                                .and("horafinal", HoraFinal).and("categoryOwner", categoryOwner)).list();
        }

        public long countByCustomClientsNull() {
                return find("customclients is not null").count();
        }

}
