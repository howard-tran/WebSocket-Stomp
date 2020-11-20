import jwt from "jsonwebtoken";
import dotenv from "dotenv";

dotenv.config();

export default (req, res, next) => {
  const authHeader = req.get("Authorization");
  if (!authHeader) {
    res.status(401);
    throw new Error("Not authenticated");
  }
  const token = req.get("Authorization").split(" ")[1];
  const secretString = process.env.SECRET;
  let decodedToken;
  try {
    decodedToken = jwt.verify(token, secretString);
  } catch (error) {
    throw new Error(error);
  }
  if (!decodedToken) {
    res.status(401);
    throw new Error("Not authenticated");
  }
  next();
};
