import { z } from "zod";

export const userSchema = z.object({
    uuid: z.string(),
    username: z.string(),
    email: z.string().email(),
    password: z.string(),
    createTime: z.string(),
    role: z.string()
});

export type User = z.infer<typeof userSchema>

export const emptyUser = userSchema.partial();
export const createUser = userSchema.partial({
    uuid:true,
    createTime:true,
    role: true
});