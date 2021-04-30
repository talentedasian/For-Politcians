package com.example.demo.dtomapper.interfaces;

public interface DTOMapper <T,X>{

	T mapToDTO(X entity);
	
}
