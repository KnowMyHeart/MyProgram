package com.itheima.bos.dao.text;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Collection;

import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.junit.Test;

import com.itheima.domain.Customer;

/**
 * ClassName:CRMTest <br/>
 * Function: <br/>
 * Date: Nov 6, 2017 9:09:05 AM <br/>
 */
public class CRMTest {

    @Test
    public void test1() {
        Collection<? extends Customer> collection =
                WebClient.create("http://localhost:8180/crm/webService/customerService/customer")
                        .accept(MediaType.APPLICATION_JSON).getCollection(Customer.class);

        for (Customer customer : collection) {
            System.out.println(customer);
        }
    }

    @Test
    public void test02() {
        Collection<? extends Customer> collection = WebClient
                .create("http://localhost:8180/crm/webService/customerService/findUnAssociatedCustomers")
                .getCollection(Customer.class);

        for (Customer customer : collection) {
            System.out.println(customer);
        }
    }

    @Test
    public void test03() {
        Collection<? extends Customer> collection = WebClient
                .create("http://localhost:8180/crm/webService/customerService/findCustomerAssociated")
                .accept(MediaType.APPLICATION_JSON).query("fixedAreaId", "222")
                .getCollection(Customer.class);

        for (Customer customer : collection) {
            System.out.println(customer);
        }
    }
    
    
    @Test
    public void test043() throws Exception{
        File file = new File("C:/Users/Administrator/Desktop/区域导入测试数据.xls");
        FileInputStream is = new FileInputStream(file);
        BufferedInputStream bs = new BufferedInputStream(is);
        HSSFWorkbook hssfWorkbook  = new HSSFWorkbook(bs);
        HSSFSheet sheetAt = hssfWorkbook.getSheetAt(0);
        for (Row row : sheetAt) {
            if (row.getRowNum() == 0) {
                continue;
            }
            String value = row.getCell(0).getStringCellValue();
            System.out.println(value);
        }
        hssfWorkbook.close();
    }
    
    
    
}
