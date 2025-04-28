import { Controller, All, Req } from '@nestjs/common';
import { Request } from 'express';
import { AppService } from '../service/app.service';

@Controller()
export class AppController {
  constructor(private readonly appService: AppService) {}

  @All()
  echo(@Req() req: Request) {
    return {
      echo: "nest",
      method: req.method,
      url: `${req.protocol}://${req.get('host')}${req.originalUrl}`,
      headers: req.headers,
      query: req.query,
      body: req.body
    };
  }
}
