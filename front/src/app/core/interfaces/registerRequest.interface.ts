/**
 * Request payload for user register.
 *
 * @remarks
 * This interface defines the shape of the data sent to the backend when
 * registering in.
 *
 * @property username
 * @property email
 * @property password - clear password
 *
 */
export interface RegisterRequest {
    username: string;
    email: string;
    password: string;
}