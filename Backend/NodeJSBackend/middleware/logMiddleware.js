import winston from "winston";
import expressWinston from "express-winston";
import path from "path";
import { dirname } from "dirname-filename-esm";

const __dirname = dirname(import.meta);

console.log(__dirname);

const serverLog = expressWinston.logger({
  transports: [
    new winston.transports.Console(),
    new winston.transports.File({
      level: "info",
      filename: path.join(__dirname, "../", "logs", "server.log"),
    }),
  ],
  format: winston.format.combine(
    winston.format.splat(),
    winston.format.align(),
    winston.format.timestamp({
      format: "YYYY-MM-DD HH:mm:ss",
    }),
    winston.format.printf((log) => {
      // nếu log là error hiển thị stack trace còn không hiển thị message của log
      if (log.stack) return `[${log.timestamp}] [${log.level}] ${log.stack}`;
      return `[${log.timestamp}] [${log.level}] ${log.message}`;
    })
  ),
  msg:
    "|{{res.statusCode}}|\t{{req.method}} \t|\t{{res.responseTime}}ms \t|{{req.url}}",
  expressFormat: false,
  colorize: false,
});

const errorLog = expressWinston.errorLogger({
  transports: [
    new winston.transports.Console(),
    new winston.transports.File({
      level: "error",
      filename: path.join(__dirname, "../", "logs", "error.log"),
    }),
  ],
  format: winston.format.combine(
    winston.format.splat(),
    winston.format.align(),
    winston.format.timestamp({
      format: "YYYY-MM-DD HH:mm:ss",
    }),
    winston.format.printf((log) => {
      // nếu log là error hiển thị stack trace còn không hiển thị message của log
      return `[${log.timestamp}] [${log.level}] ${log.message}`;
    })
  ),
  msg: "|{{res.statusCode}}|\t{{req.method}} \t|\t0ms \t|{{req.url}} | {{err}}",
  expressFormat: false,
  colorize: false,
  dumpExceptions: true,
  showStack: true,
});

export { serverLog, errorLog };
