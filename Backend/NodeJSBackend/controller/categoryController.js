import mongoose from "mongoose";
import Category from "../models/categoryModel.js";
import asyncHandler from "express-async-handler";
export const createCategory = asyncHandler(async (req, res) => {
  const newCategory = new Category({
    _id: mongoose.Types.ObjectId(),
    name: req.body.name,
    properties: req.body.properties,
  });

  try {
    await Category.insertMany(newCategory);

    console.log("Added new category");
    res.status(201).json({
      message: "Category created successfully!",
    });
  } catch (error) {
    console.error(`${error}`);
  }


});
