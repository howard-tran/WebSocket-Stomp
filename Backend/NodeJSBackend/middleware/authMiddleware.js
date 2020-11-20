import jwt from "jsonwebtoken";
import dotenv from "dotenv";

dotenv.config();

export default (req, res, next) => {
  const token = req.get("Authorization").split(" ")[1];
  const secretString = process.env.SECRET;
  let decodedToken;
  try {
    decodedToken = jwt.verify(token, secretString);
  } catch (error) {
    throw new Error(error);
  }
  if (!decodedToken) {
    const error = new Error("Not authenticated");
    res.status(401);
    throw error;
  }
  next();
};
