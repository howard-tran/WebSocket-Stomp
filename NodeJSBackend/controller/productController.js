import mongoose from "mongoose";
import Product from "../models/productModel.js";
import asyncHandler from "express-async-handler";

export const createProduct = asyncHandler(async (req, res) => {
  try {
    const newProduct = new Product({
      _id: mongoose.Types.ObjectId(),
      ...req.body,
    });
    await Product.insertMany(newProduct);

    console.log("Added new product");
    res.status(201).json({
      message: "product created successfully!",
    });
  } catch (error) {
    throw new Error(error);
  }
});
