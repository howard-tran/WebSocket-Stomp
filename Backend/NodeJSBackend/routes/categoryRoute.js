import express from "express";
import Category from "../models/categoryModel.js";
import asyncHandler from "express-async-handler";
import { createCategory } from "../controller/categoryController.js";

const router = express.Router();

// @desc Fetch all categories
// @route GET /api/categories
// @access Public
router.get(
  "/",
  asyncHandler(async (req, res) => {
    const categories = await Category.find({});
    res.json(categories);
  })
);

// @desc Fetch single category
// @route GET /api/categories/:id
// @access Public
router.get(
  "/:id",
  asyncHandler(async (req, res) => {
    const category = await Category.findById(req.params.id);

    if (category) {
      res.json(category);
    } else {
      res.status(404);
      throw new Error("Category not found");
    }
  })
);

router.post("/", createCategory);

export default router;
