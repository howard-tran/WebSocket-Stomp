const path = require("path");

module.exports = {
  entry: {
    // index: './src/index.ts',
    signup: "./src/signup.ts",
  },
  module: {
    rules: [
      // == typescript loader ==

      {
        test: /\.tsx?$/,
        use: "ts-loader",
        exclude: /node_modules/,
      },
    ],
  },
  resolve: {
    extensions: [".tsx", ".ts", ".js"],
  },
  output: {
    filename: "[name].js",
    path: path.resolve(__dirname, "dist"),
  },
};
