package com.david.drxtransportsolution.dtos;

import com.david.drxtransportsolution.entities.Transport;
import com.david.drxtransportsolution.entities.Transport.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransportDTO {
    private long transportId;
    private long driverId;
    private long locationId;
    private Transport.Status status;
    private LocalDate dispatchDate;
    private LocalDate deliveryDate;
}
