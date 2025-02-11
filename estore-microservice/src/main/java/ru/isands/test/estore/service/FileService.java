package ru.isands.test.estore.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import ru.isands.test.estore.dto.*;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Service
@AllArgsConstructor
public class FileService {

    private final ShopService shopService;
    private final PurchaseTypeService purchaseTypeService;
    private final PurchaseService purchaseService;
    private final PositionTypeService positionTypeService;
    private final EmployeeService employeeService;
    private final ElectroTypeService electroTypeService;
    private final ElectroShopService electroShopService;
    private final ElectroItemService electroItemService;
    private final ElectroEmployeeService electroEmployeeService;

    public boolean saveFile(@RequestPart(value = "file") final MultipartFile file) {
        boolean state = false;
        String searchedFileName = "Shop.csv";
        Map<String, byte[]> fileContents = new HashMap<>();

        try (ZipInputStream zis = new ZipInputStream(new ByteArrayInputStream(file.getBytes()))) {
            for (ZipEntry entry; (entry = zis.getNextEntry()) != null; ) {
                state = true;
                if (entry.getName().equals(searchedFileName)) {
                    state = readCSVFromBytes(zis.readAllBytes(), entry.getName());
                    searchedFileName = getNextFileNameToSearch(searchedFileName);
                } else {
                    fileContents.put(entry.getName(), zis.readAllBytes());
                }

                zis.closeEntry();
                if (!state || searchedFileName == null)
                    break;
            }
            if (fileContents.size() > 0) {
                state = readFilesFromCache(fileContents, searchedFileName);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return state;
    }

    private boolean readFilesFromCache(Map<String, byte[]> fileContents, String searchedFileName) throws IOException {
        boolean state = true;

        while (state) {
            state = false;
            String startFileName = searchedFileName;
            for (Map.Entry<String, byte[]> entry : fileContents.entrySet()) {
                state = true;
                if (entry.getKey().equals(searchedFileName)) {
                    state = readCSVFromBytes(entry.getValue(), entry.getKey());
                    searchedFileName = getNextFileNameToSearch(searchedFileName);
                }
                if (!state)
                    break;
            }
            if (searchedFileName == null || !state) {
                break;
            }
            if (startFileName.equals(searchedFileName)) {
                state = false;
            }
        }
        return state;
    }

    private String getNextFileNameToSearch(String fileName) {
        if (fileName.startsWith("Shop")) {
            return "PurchaseType.csv";
        } else if (fileName.startsWith("PurchaseType")) {
            return "PositionType.csv";
        } else if (fileName.startsWith("PositionType")) {
            return "Employee.csv";
        } else if (fileName.startsWith("Employee")) {
            return "ElectroType.csv";
        } else if (fileName.startsWith("ElectroType")) {
            return "ElectroItem.csv";
        } else if (fileName.startsWith("ElectroItem")) {
            return "ElectroShop.csv";
        } else if (fileName.startsWith("ElectroShop")) {
            return "ElectroEmployee.csv";
        } else if (fileName.startsWith("ElectroEmployee")) {
            return "Purchase.csv";
        }

        return null;
    }

    private boolean readCSVFromBytes(byte[] csvData, String fileName) throws IOException {
        System.out.println(fileName);
        boolean state = false;
        try (InputStream inputStream = new ByteArrayInputStream(csvData);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "Windows-1251"))) {

            String line;
            int i = 0;
            while ((line = reader.readLine()) != null) {
                if (i == 0) {
                    ++i;
                    continue;
                }
                String[] values = line.split(";");
                if (values.length == 1) {
                    values = line.split(",");
                }
                state = updateRecords(values, fileName);
                if (!state) {
                    break;
                }
                ++i;
            }
        }
        return state;
    }

    private boolean updateRecords(String[] values, String fileName) {
        boolean state = true;
        if (fileName.startsWith("Shop")) {
            shopService.save(new ShopDTO(Long.parseLong(values[0]), values[1], values[2]));
        } else if (fileName.startsWith("PurchaseType")) {
            purchaseTypeService.save(new PurchaseTypeDTO(Long.parseLong(values[0]), values[1]));
        } else if (fileName.startsWith("PositionType")) {
            positionTypeService.save(new PositionTypeDTO(Long.parseLong(values[0]), values[1]));
        } else if (fileName.startsWith("Employee")) {
            employeeService.save(new EmployeeDTO(Long.parseLong(values[0]), values[1], values[2], values[3],
                    LocalDate.parse(values[4], DateTimeFormatter.ofPattern("dd.MM.yyyy")),
                    Long.parseLong(values[5]), Long.parseLong(values[6]), values[7].equals("1")));
        } else if (fileName.startsWith("ElectroType")) {
            electroTypeService.save(new ElectroTypeDTO(Long.parseLong(values[0]), values[1]));
        } else if (fileName.startsWith("ElectroItem")) {
            electroItemService.save(new ElectroItemDTO(Long.parseLong(values[0]), values[1],
                    Long.parseLong(values[2]), Long.parseLong(values[3]), Integer.parseInt(values[4]),
                    Boolean.parseBoolean(values[5]), values[6]));
        } else if (fileName.startsWith("ElectroShop")) {
            electroShopService.save(new ElectroShopDTO(Long.parseLong(values[0]), Long.parseLong(values[1]),
                    Integer.parseInt(values[2])));
        } else if (fileName.startsWith("ElectroEmployee")) {
            electroEmployeeService.save(new ElectroEmployeeDTO(Long.parseLong(values[0]), Long.parseLong(values[1])));
        } else if (fileName.startsWith("Purchase")) {
            PurchaseDTO dto = new PurchaseDTO();
            dto.setId(Long.parseLong(values[0]));
            dto.setElectroItem(Long.parseLong(values[1]));
            dto.setEmployee(Long.parseLong(values[2]));
            dto.setPurchaseDate(LocalDate.parse(values[3], DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")));
            dto.setPurchaseType(Long.parseLong(values[4]));
            dto.setShop(Long.parseLong(values[5]));

            purchaseService.saveWithoutItemCheck(dto);
        }else {
            state = false;
        }

        return state;
    }
}
