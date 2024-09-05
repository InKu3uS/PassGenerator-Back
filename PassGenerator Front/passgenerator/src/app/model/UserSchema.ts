import { z } from "zod";

export const userSchema = z.object({
    uuid: z.string().optional(),
    username: z.string(),
    email: z.string().email(),
    password: z.string(),
    createTime: z.string().optional(),
    role: z.string().optional(),
});

export type User = z.infer<typeof userSchema>

export const emptyUser = userSchema.partial();
export const createUser = userSchema.partial({
    uuid:true,
    createTime:true,
    role: true
});
