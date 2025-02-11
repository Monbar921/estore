package ru.isands.test.estore.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.isands.test.estore.dao.entity.*;
import ru.isands.test.estore.dao.repo.ElectroEmployeeRepository;
import ru.isands.test.estore.dto.ElectroEmployeeDTO;
import ru.isands.test.estore.exception.EntityNotExistsException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ElectroEmployeeService extends BaseService<ElectroEmployee, Long>{
    private final ElectroTypeService electroTypeService;
    private final EmployeeService employeeService;

    public ElectroEmployeeService(ElectroEmployeeRepository repository, ModelMapper modelMapper,
                                  ElectroTypeService electroTypeService, EmployeeService employeeService) {
        super(repository, modelMapper);
        this.electroTypeService = electroTypeService;
        this.employeeService = employeeService;
    }
    @Transactional
    public void save(ElectroEmployeeDTO dto) {
        Employee employee = employeeService.findById(dto.getEmployee()).orElseThrow(
                () -> new EntityNotExistsException("Such employee does not exists")
        );

        ElectroType electroType = electroTypeService.findById(dto.getElectroType()).orElseThrow(
                () -> new EntityNotExistsException("Such electro type does not exists")
        );

        Optional<ElectroEmployee> existingEntity = ((ElectroEmployeeRepository)getBaseRepository())
                .findByEmployeeAndElectroType(employee, electroType);

        ElectroEmployeePK pk = new ElectroEmployeePK(dto.getEmployee(), dto.getElectroType());
        ElectroEmployee entity = new ElectroEmployee(pk, employee, electroType);

        super.save(entity, existingEntity.isPresent());
    }

    public List<ElectroEmployeeDTO> findAllDto(int page, int size) {
        List<ElectroEmployee> entities = super.findAll(page, size);
        return entities.stream().map(ElectroEmployeeDTO::new).collect(Collectors.toList());
    }

    @Transactional
    public void deleteByEmployeeAndElectroType(Long electroEmployeeId, Long electroTypeId) {
        Employee employee = employeeService.findById(electroEmployeeId).orElseThrow(
                () -> new EntityNotExistsException("Such employee does not exists")
        );

        ElectroType electroType = electroTypeService.findById(electroTypeId).orElseThrow(
                () -> new EntityNotExistsException("Such electro type does not exists")
        );

        ((ElectroEmployeeRepository)getBaseRepository()).deleteByEmployeeAndElectroType(employee, electroType);
    }
}
