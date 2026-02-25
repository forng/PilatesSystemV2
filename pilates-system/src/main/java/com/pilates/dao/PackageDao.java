package com.pilates.dao;
import com.pilates.model.Package;

import java.util.List;

public interface PackageDao {
	List<Package> findAll() throws Exception;
}
