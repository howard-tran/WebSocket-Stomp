import mongoose from "mongoose";
import Category from "../models/categoryModel.js";
import asyncHandler from "express-async-handler";
export const createCategory = asyncHandler(async (req, res) => {
  try {
    const newCategory = new Category({
      _id: mongoose.Types.ObjectId(),
      ...req.body,
    });
    await Category.insertMany(newCategory);

    console.log("Added new category");
    res.status(201).json({
      message: "Category created successfully!",
    });
  } catch (error) {
    res.status(500);

    throw new Error(error);
  }
});
