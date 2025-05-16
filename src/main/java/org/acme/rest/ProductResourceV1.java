package org.acme.rest;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import org.acme.entity.Product;
import org.acme.repository.ProductRepository;
import org.acme.util.panache.PanacheQueryFactory;
import org.acme.util.query.*;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import java.util.*;

@Path("/api/v1/products")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductResourceV1 {

    @Inject
    ProductRepository productRepository;

    @GET
    @Operation(summary = "returns the product with the specified id")
    @APIResponse(responseCode = "200", description = "Getting the product with the specified id successful")
    @APIResponse(responseCode = "500", description = "Server unavailable")
    @Path("/{productId}")
    public Product getOne(Long productId) throws NotFoundException {
        Product product = productRepository.findById(productId);
        if (product == null) {
            throw new NotFoundException();
        }
        return product;
    }

    @POST
    @Operation(summary = "creates a new product")
    @APIResponse(responseCode = "   201", description = "product creation successful")
    @APIResponse(responseCode = "500", description = "Server unavailable")
    @Path("/")
    @Transactional
    public Product create(Product product) {
        productRepository.persistAndFlush(product);
        return product;
    }

    @PUT
    @Operation(summary = "updates an existing product")
    @APIResponse(responseCode = "200", description = "product update successful")
    @APIResponse(responseCode = "500", description = "Server unavailable")
    @Path("/{productId}")
    @Transactional
    public Product update(Product product) {
        Product existingProduct = productRepository.findById(product.productId);
        existingProduct.updatedAt = product.updatedAt;
        existingProduct.price = product.price;
        productRepository.persistAndFlush(existingProduct);
        return existingProduct;
    }

    @DELETE
    @Path("/{productId}")
    @Operation(summary = "deletes an existing product")
    @APIResponse(responseCode = "204", description = "product delete successful")
    @APIResponse(responseCode = "500", description = "Server unavailable")
    @Transactional
    public Boolean delete(Long productId) {
        return productRepository.deleteById(productId);
    }

    @GET
    @Path("/")
    @Operation(summary = "returns a list of all products")
    @APIResponse(responseCode = "200", description = "List of all products successful")
    @APIResponse(responseCode = "500", description = "Server unavailable")
    @Transactional
    public List<Product> query(@BeanParam QueryParameters queryParameters) {
        try {
            Set<String> eagerRelations = Set.of("productLangs"); // or emptySet() if nothing
            PanacheQueryFactory factory = new PanacheQueryFactory(Product.class);
            PanacheQuery<Product> panacheQuery = factory.createFromQueryParameters(productRepository, queryParameters, eagerRelations);
            List<Product> result = panacheQuery.list();
            return result;
        } catch (Exception e) {
            throw new InternalServerErrorException();
        }
    }

}
