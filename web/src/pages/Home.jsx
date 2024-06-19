import React, { useState, useEffect } from "react";
import {
  Box,
  Typography,
  Card,
  TextField,
  Button,
  Switch,
  FormControlLabel,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Paper,
  Select,
  MenuItem,
  InputLabel,
  Modal,
  IconButton,
  Tooltip,
  InputAdornment,
  TablePagination,
  TableSortLabel,
} from "@mui/material";
import DeleteIcon from "@mui/icons-material/Delete";
import EditIcon from "@mui/icons-material/Edit";
import axios from "axios";
import { jwtDecode } from "jwt-decode";
import Header from "./Header";

export function Home() {
  const initialFormState = {
    id: null,
    name: "",
    price: "",
    categoryId: "",
    description: "",
    available: false,
  };

  const [formData, setFormData] = useState(initialFormState);
  const [products, setProducts] = useState([]);
  const [categories, setCategories] = useState([]);
  const [modalOpen, setModalOpen] = useState(false);
  const [deleteProductId, setDeleteProductId] = useState(null);
  const [editProductId, setEditProductId] = useState(null);
  const [userRole, setUserRole] = useState(null);

  const [formErrors, setFormErrors] = useState({
    name: "",
    price: "",
    categoryId: "",
    description: "",
  });

  const [page, setPage] = useState(0);
  const [rowsPerPage, setRowsPerPage] = useState(5);

  const [searchTerm, setSearchTerm] = useState("");

  const [orderBy, setOrderBy] = useState("name");
  const [order, setOrder] = useState("asc");

  useEffect(() => {
    const fetchCategories = async () => {
      try {
        const token = localStorage.getItem("token");
        const response = await axios.get(
          "http://localhost:8080/api/categories",
          {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          }
        );
        setCategories(response.data);
      } catch (error) {
        console.error("Error fetching categories:", error);
      }
    };

    fetchCategories();
  }, []);

  useEffect(() => {
    const fetchProducts = async () => {
      try {
        const token = localStorage.getItem("token");
        const response = await axios.get(
          "http://localhost:8080/api/products",
          {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          }
        );
        setProducts(response.data);

        const decodedToken = jwtDecode(token);
        setUserRole(decodedToken.role);
      } catch (error) {
        console.error("Error fetching products:", error);
      }
    };

    fetchProducts();
  }, []);

  const handleChange = (e) => {
    const { name, value, type, checked } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: type === "checkbox" ? checked : value,
    }));
  };

  const validateForm = () => {
    let valid = true;
    const errors = {
      name: "",
      price: "",
      categoryId: "",
      description: "",
    };

    if (!formData.name) {
      errors.name = "Name is required";
      valid = false;
    }
    if (!formData.price) {
      errors.price = "Price is required";
      valid = false;
    }
    if (!formData.categoryId) {
      errors.categoryId = "Category is required";
      valid = false;
    }
    if (!formData.description) {
      errors.description = "Description is required";
      valid = false;
    }

    setFormErrors(errors);
    return valid;
  };

  const handleFormSubmit = () => {
    if (validateForm()) {
      if (formData.id) {
        editProduct(formData);
      } else {
        saveProduct(formData);
      }
      setFormData(initialFormState);
    }
  };

  const saveProduct = async (productData) => {
    try {
      const token = localStorage.getItem("token");
      const response = await axios.post(
        "http://localhost:8080/api/products",
        productData,
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );

      setProducts([...products, response.data]);
    } catch (error) {
      console.error("Error saving product:", error);
    }
  };

  const editProduct = async (productData) => {
    try {
      const token = localStorage.getItem("token");
      const response = await axios.put(
        `http://localhost:8080/api/products/${productData.id}`,
        productData,
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );

      const updatedProducts = products.map((product) =>
        product.id === response.data.id ? response.data : product
      );
      setProducts(updatedProducts);
    } catch (error) {
      console.error("Error editing product:", error);
    }
  };

  const openDeleteModal = (productId) => {
    setDeleteProductId(productId);
    setModalOpen(true);
  };

  const closeDeleteModal = () => {
    setDeleteProductId(null);
    setModalOpen(false);
  };

  const deleteProduct = async () => {
    try {
      const token = localStorage.getItem("token");
      await axios.delete(
        `http://localhost:8080/api/products/${deleteProductId}`,
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );
      console.log("Product deleted:", deleteProductId);
      const updatedProducts = products.filter(
        (product) => product.id !== deleteProductId
      );
      setProducts(updatedProducts);
      closeDeleteModal();
    } catch (error) {
      console.error("Error deleting product:", error);
    }
  };

  const openEditForm = (productId) => {
    const productToEdit = products.find((product) => product.id === productId);
    if (productToEdit) {
      setFormData({
        id: productToEdit.id,
        name: productToEdit.name,
        price: productToEdit.price,
        categoryId: productToEdit.categoryId,
        description: productToEdit.description,
        available: productToEdit.available,
      });
      setEditProductId(productId);
    }
  };

  const cancelEdit = () => {
    setFormData(initialFormState);
    setEditProductId(null);
  };

  const handleChangePage = (event, newPage) => {
    setPage(newPage);
  };

  const handleChangeRowsPerPage = (event) => {
    setRowsPerPage(parseInt(event.target.value, 10));
    setPage(0); 
  };

  const handleSearchChange = (event) => {
    setSearchTerm(event.target.value);
    setPage(0); 
  };

  const handleRequestSort = (property) => {
    const isAsc = orderBy === property && order === "asc";
    setOrderBy(property);
    setOrder(isAsc ? "desc" : "asc");
  };

  const sortedProducts = () => {
    const comparator = (a, b) => {
      if (order === "asc") {
        return a[orderBy] < b[orderBy] ? -1 : 1;
      } else {
        return a[orderBy] > b[orderBy] ? -1 : 1;
      }
    };

    return [...products].sort(comparator);
  };

  const filteredProducts = () => {
    return sortedProducts().filter((product) =>
      product.name.toLowerCase().includes(searchTerm.toLowerCase())
    );
  };

  const paginatedProducts = () => {
    return filteredProducts().slice(
      page * rowsPerPage,
      page * rowsPerPage + rowsPerPage
    );
  };

  return (
    <>
      <Header />
      <Box height="95vh" sx={{ padding: 2 }}>
        <Typography variant="h4" sx={{ textAlign: "center", marginY: 2 }}>
          Product Registration
        </Typography>

        <Box
          display="flex"
          justifyContent="center"
          alignItems="flex-start"
          gap={4}
        >
          <Card sx={{ p: 3, width: "100%", boxShadow: 3 }}>
            <Typography variant="h6" gutterBottom>
              {editProductId ? "Edit Product" : "Register New Product"}
            </Typography>

            <Box display="flex" flexDirection="column" gap={2}>
              <Box display="flex" gap={1} sx={{ maxWidth: "600px" }}>
                <TextField
                  size="small"
                  fullWidth
                  label="Name"
                  name="name"
                  value={formData.name}
                  onChange={handleChange}
                  error={!!formErrors.name}
                  helperText={formErrors.name}
                />
                <TextField
                  size="small"
                  fullWidth
                  label="Price"
                  name="price"
                  type="number"
                  value={formData.price}
                  onChange={handleChange}
                  error={!!formErrors.price}
                  helperText={formErrors.price}
                  InputProps={{
                    startAdornment: (
                      <InputAdornment position="start">$</InputAdornment>
                    ),
                  }}
                />
              </Box>
              <InputLabel id="category-label">Category</InputLabel>
              <Select
                labelId="category-label"
                id="category"
                fullWidth
                name="categoryId"
                value={formData.categoryId}
                onChange={handleChange}
                error={!!formErrors.categoryId}
              >
                {categories.map((category) => (
                  <MenuItem key={category.id} value={category.id}>
                    {category.name}
                  </MenuItem>
                ))}
              </Select>
              {formErrors.categoryId && (
                <Typography variant="caption" color="error">
                  {formErrors.categoryId}
                </Typography>
              )}
              <TextField
                size="small"
                fullWidth
                label="Description"
                name="description"
                value={formData.description}
                onChange={handleChange}
                error={!!formErrors.description}
                helperText={formErrors.description}
              />
              <Box display="flex" flexDirection="column" alignItems="flex-end">
                <FormControlLabel
                  control={<Switch color="primary" />}
                  label="Available"
                  labelPlacement="start"
                  name="available"
                  checked={formData.available}
                  onChange={handleChange}
                />
                {editProductId ? (
                  <Box>
                    <Button
                      variant="contained"
                      color="primary"
                      onClick={handleFormSubmit}
                      style={{ marginRight: 10 }}
                    >
                      Update
                    </Button>
                    <Button variant="outlined" onClick={cancelEdit}>
                      Cancel
                    </Button>
                  </Box>
                ) : (
                  <Button
                    variant="contained"
                    color="primary"
                    onClick={handleFormSubmit}
                  >
                    Register
                  </Button>
                )}
              </Box>
            </Box>
          </Card>
        </Box>

        <Box mt={4}>
          <Typography variant="h6" sx={{ textAlign: "center", marginBottom: 2 }}>
            Product List
          </Typography>

          <Box sx={{ px: 2, pb: 2 }}>
            {/* Campo de Pesquisa */}
            <TextField
              size="small"
              fullWidth
              label="Search"
              value={searchTerm}
              onChange={handleSearchChange}
            />
          </Box>

          <TableContainer component={Paper}>
            <Table>
              <TableHead>
                <TableRow>
                  <TableCell>
                    <TableSortLabel
                      active={orderBy === "name"}
                      direction={orderBy === "name" ? order : "asc"}
                      onClick={() => handleRequestSort("name")}
                    >
                      Name
                    </TableSortLabel>
                  </TableCell>
                  <TableCell align="right">
                    <TableSortLabel
                      active={orderBy === "price"}
                      direction={orderBy === "price" ? order : "asc"}
                      onClick={() => handleRequestSort("price")}
                    >
                      Price
                    </TableSortLabel>
                  </TableCell>
                  <TableCell align="right">Category</TableCell>
                  <TableCell>Description</TableCell>
                  <TableCell align="right">Available</TableCell>
                  <TableCell>Action</TableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                {paginatedProducts().map((product) => (
                  <TableRow key={product.id}>
                    <TableCell component="th" scope="row">
                      {product.name}
                    </TableCell>
                    <TableCell align="right">${product.price}</TableCell>
                    <TableCell align="right">
                      {
                        categories.find((cat) => cat.id === product.categoryId)
                          ?.name
                      }
                    </TableCell>
                    <TableCell>{product.description}</TableCell>
                    <TableCell align="right">
                      {product.available ? "Yes" : "No"}
                    </TableCell>
                    <TableCell>
                      <IconButton
                        color="primary"
                        aria-label="edit product"
                        onClick={() => openEditForm(product.id)}
                      >
                        <EditIcon />
                      </IconButton>
                      {userRole === "ADMIN" ? (
                        <IconButton
                          color="error"
                          aria-label="delete product"
                          onClick={() => openDeleteModal(product.id)}
                        >
                          <DeleteIcon />
                        </IconButton>
                      ) : (
                        <Tooltip title="You do not have permission to delete">
                          <span>
                            <IconButton disabled>
                              <DeleteIcon />
                            </IconButton>
                          </span>
                        </Tooltip>
                      )}
                    </TableCell>
                  </TableRow>
                ))}
              </TableBody>
            </Table>
          </TableContainer>

          {/* Paginação */}
          <TablePagination
            rowsPerPageOptions={[5, 10, 25]}
            component="div"
            count={filteredProducts().length}
            rowsPerPage={rowsPerPage}
            page={page}
            onPageChange={handleChangePage}
            onRowsPerPageChange={handleChangeRowsPerPage}
          />
        </Box>

        <Modal
          open={modalOpen}
          onClose={closeDeleteModal}
          aria-labelledby="delete-product-modal"
          aria-describedby="modal to confirm product deletion"
        >
          <Box
            sx={{
              position: "absolute",
              top: "50%",
              left: "50%",
              transform: "translate(-50%, -50%)",
              width: 400,
              bgcolor: "background.paper",
              boxShadow: 24,
              p: 4,
              textAlign: "center",
            }}
          >
            <Typography variant="h6" gutterBottom>
              Are you sure you want to delete this product?
            </Typography>
            <Box mt={2}>
              <Button
                variant="contained"
                onClick={deleteProduct}
                color="error"
                style={{ marginRight: 10 }}
              >
                Delete
              </Button>
              <Button variant="outlined" onClick={closeDeleteModal}>
                Cancel
              </Button>
            </Box>
          </Box>
        </Modal>
      </Box>
    </>
  );
}

export default Home;
