package com.fixwi.fixwi_backend.domain.dto;

public class TicketMetricsDTO {
    private Long totalTickets;
    private Long openTickets;
    private Long closeTickets;
    private Long inProgressTickets;

    public TicketMetricsDTO(Long totalTickets, Long openTickets, Long closeTickets, Long inProgressTickets) {
        this.totalTickets = totalTickets;
        this.openTickets = openTickets;
        this.closeTickets = closeTickets;
        this.inProgressTickets = inProgressTickets;
    }

    public TicketMetricsDTO() {
    }

    public Long getTotalTickets() {
        return totalTickets;
    }

    public void setTotalTickets(Long totalTickets) {
        this.totalTickets = totalTickets;
    }

    public Long getOpenTickets() {
        return openTickets;
    }

    public void setOpenTickets(Long openTickets) {
        this.openTickets = openTickets;
    }



    public Long getInProgressTickets() {
        return inProgressTickets;
    }

    public void setInProgressTickets(Long inProgressTickets) {
        this.inProgressTickets = inProgressTickets;
    }

    public Long getCloseTickets() {
        return closeTickets;
    }

    public void setCloseTickets(Long closeTickets) {
        this.closeTickets = closeTickets;
    }
}
