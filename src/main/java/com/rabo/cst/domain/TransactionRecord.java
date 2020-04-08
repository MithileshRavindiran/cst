package com.rabo.cst.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.xml.bind.annotation.*;
import java.math.BigDecimal;

/**
 * Created by mravindran on 08/04/20.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@XmlRootElement(name = "record")
@XmlAccessorType(XmlAccessType.FIELD)
@ApiModel(description = "All details about the Transaction Record")
public class TransactionRecord {
    @EqualsAndHashCode.Include
    @XmlAttribute(name = "reference")
    @ApiModelProperty(notes ="Reference an unique number for the Transaction")
    private Long reference;
    @XmlElement(name = "accountNumber")
    @ApiModelProperty(notes ="AccountNumber from/to  which the transaction is done")
    private String accountNumber;
    @XmlElement(name = "description")
    @ApiModelProperty(notes ="Description about the transaction")
    private String description;
    @XmlElement(name = "startBalance")
    @ApiModelProperty(notes ="Inital Balance at the beginning of the transaction")
    private BigDecimal startBalance;
    @XmlElement(name = "mutation")
    @ApiModelProperty(notes ="Amount which either deducted/credited as part of transaction")
    private BigDecimal mutation;
    @XmlElement(name = "endBalance")
    @ApiModelProperty(notes ="Final Balance after the transaction is done")
    private BigDecimal endBalance;

    @ApiModelProperty(notes ="Indicates if the  transaction is valid or not")
    private Boolean isInvalidRecord;

    @ApiModelProperty(notes ="Description when the transaction is invalid")
    private String reason;


}
