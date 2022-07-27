package ks43team03.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Reservation {
	String reservationCd;
	String facilityGoodsCd;
	String reservationId;
	String reservationDate;
	String reservationStartTime;
	String reservationEndTime;
	String reservationCancel;
}
