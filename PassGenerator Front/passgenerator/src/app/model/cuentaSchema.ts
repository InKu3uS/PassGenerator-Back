import { z } from "zod"

export const cuentaSchema = z.object({
    id: z.string().optional(),
    user_uuid: z.string(),
    create_time: z.string(),
    expiration_time: z.string(),
    site: z.string(),
    password: z.string(),
});

export type Cuenta = z.infer<typeof cuentaSchema>

export const emptyCuenta = cuentaSchema.partial();
export const createCuenta = cuentaSchema.partial({
    id:true,
    create_time:true
})