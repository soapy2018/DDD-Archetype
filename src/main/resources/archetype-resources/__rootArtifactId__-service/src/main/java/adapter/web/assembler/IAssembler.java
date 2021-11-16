package ${package}.adapter.web.assembler;


import com.bluetron.nb.common.base.dto.LoginUserDTO;

public interface IAssembler<R, T> {

    T apply(LoginUserDTO loginUserDTO, R r);
}
