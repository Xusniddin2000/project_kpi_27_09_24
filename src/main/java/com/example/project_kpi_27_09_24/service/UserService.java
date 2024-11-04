package com.example.project_kpi_27_09_24.service;



import com.example.project_kpi_27_09_24.dto.UserRequest;
import com.example.project_kpi_27_09_24.dto.api.ApiResponse;
import com.example.project_kpi_27_09_24.dto.api.ApiResponseModel;
import com.example.project_kpi_27_09_24.entity.auth.User;
import com.example.project_kpi_27_09_24.entity.enums.ResponseType;
import com.example.project_kpi_27_09_24.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> all() {

        return userRepository.findAll();
    }

    public ApiResponse getUser(Long id) {
        ApiResponse result = new ApiResponse();
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            result.setSuccess(true);
            result.setObject(user.get());
            result.setResponseType(ResponseType.SUCCESS);
            return result;
        }

          result.setSuccess(false);
          result.setResponseType(ResponseType.DANGER);
          result.setMessage("Bu User topilmadi!!!");
           return  result;
    }

    public ApiResponseModel checkUser(String userName) {
        ApiResponseModel result = new ApiResponseModel();
        userRepository.findByUserName(userName).ifPresentOrElse(
                user -> {
                    result.setSuccess(false);
                    result.setObject(user);
                    result.setMessage("Ushbu Username mavjud!! !");
                    result.setResponseType(ResponseType.SUCCESS);
                },
                () -> {
                    result.setSuccess(true);
                    result.setMessage("Ushbu Username  mavjud emas  ");
                    result.setResponseType(ResponseType.INFO);
                }
        );
        return result;
    }

    public Page<User> getUserPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.ASC, "createdAt");
        return userRepository.findAll(pageable);
    }




    public ApiResponse save(UserRequest userRequest) {
        ApiResponse apiResponse = new ApiResponse();
        Optional<User> userOptional = userRepository.findByUserName(userRequest.getUserName());
        if (userOptional.isPresent()) {
            apiResponse.setSuccess(false);
            apiResponse.setMessage("Bu UserName  avval  kiritilgan, boshqa UserName kiriting : ");
            apiResponse.setResponseType(ResponseType.WARNING);
            return apiResponse;
        }
        User user = new User();
        user.getId();
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setUserName(userRequest.getUserName());
        user.setPassword(userRequest.getPassword());
        user.setEmail(userRequest.getEmail());
        user.setPhoneNumber(userRequest.getPhoneNumber());
        userRepository.save(user);
        apiResponse.setSuccess(true);
        apiResponse.setMessage("Bu User  Saqlandi !!!");
        apiResponse.setResponseType(ResponseType.SUCCESS);
        return apiResponse;
    }


    public ApiResponse getByUserId(Long id) {
        ApiResponse apiResponse = new ApiResponse();
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            apiResponse.setSuccess(true);
            apiResponse.setMessage("User  topildi....!!!! ");
            apiResponse.setObject(userOptional.get());
            return apiResponse;
        }
        apiResponse.setSuccess(false);
        apiResponse.setMessage("Bu Idli User  topilmadi...!!!!");
        return apiResponse;
    }

    public   Optional<User> getOptionalById(Long userId){
        return   userRepository.findById(userId);
    }


    public ApiResponse edit(Long  id, UserRequest userRequest) {
        ApiResponse apiResponse =new ApiResponse();
        User   user=userRepository.getReferenceById(id);
          user.setFirstName(userRequest.getFirstName());
          user.setLastName(userRequest.getLastName());
          user.setEmail(userRequest.getEmail());
          user.setPhoneNumber(userRequest.getPhoneNumber());
          user.setUserName(userRequest.getUserName());
          user.setPassword(userRequest.getPassword());
       User userSave= userRepository.save(user);
        apiResponse.setSuccess(true);
        apiResponse.setMessage("Bu  User O'zgartirildi....!!!!");
        apiResponse.setResponseType(ResponseType.INFO);
        apiResponse.setObject(userSave);
        return apiResponse;
    }



   public ApiResponse delete(Long id){
        ApiResponse apiResponse =new ApiResponse();
      Optional<User>   userOptional=userRepository.findById(id);
      if(!userOptional.isPresent()){
              apiResponse.setSuccess(false);
              apiResponse.setResponseType(ResponseType.DANGER);
              apiResponse.setMessage("Bu idli User Bazadan  topilmadi..!!!!");
               return apiResponse;
      }

      User  userDelete=userRepository.findById(id).get();
        userDelete.setDeleted(true);
        userRepository.save(userDelete);
        apiResponse.setSuccess(true);
        apiResponse.setResponseType(ResponseType.INFO);
        apiResponse.setMessage(" Bu User o'chirildi..!!>>");

        return   apiResponse;
    }


 /*   public   ApiResponse  deleteById(Long id){
        userRepository.deleteById(id);

        return   new ApiResponse(true,"Bu User o'chirildi..!!>>");

    }
*/
}
