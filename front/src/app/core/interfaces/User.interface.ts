
/**
 * Reprensent the user shape used when getting profile informations
 * 
 * @property id - Unique identifier of the user
 * @property username - the unique username
 * @property email -  the unique user email
 */
export interface User {
    id: number;
    username: string;
    email: string;
}

/**
 * Request payload for cupdating user informations like username, email or password
 *
 *
 * @property username – the unique username
 * @property email -  the unique user email
 * @property email -  Optional password - If set, the backend will try to change it
 *
 */
export interface UserUpdateRequest {
    username: string;
    email: string;
    password?: string;
}