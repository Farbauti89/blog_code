package dev.jschmitz.springbootservicetest;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
interface PackageRepository extends CrudRepository<Package, String> {

}
