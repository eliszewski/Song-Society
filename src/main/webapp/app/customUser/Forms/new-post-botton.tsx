import React, { useState } from 'react';
import axios from 'axios';
import { Button, Modal, ModalHeader, ModalBody, ModalFooter, Form, FormGroup, Label, Input } from 'reactstrap';
import { IPost } from 'app/shared/model/post.model';
import { IReply } from 'app/shared/model/reply.model';
import { IUser } from 'app/shared/model/user.model';
import 'app/entities/post/post-buttons.scss';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

interface PostButtonProps {
  author: IUser;
}

const PostButton: React.FC<PostButtonProps> = ({ author }) => {
  const [showModal, setShowModal] = useState(false);
  const [content, setContent] = useState('');

  const handleSubmit = async (event: any) => {
    event.preventDefault();
    const post: IPost = {
      date: new Date().toISOString(),
      content,
      user: author,
    };

    try {
      await axios.post('/api/posts', post);
      setContent('');
      setShowModal(false);
    } catch (error) {
      console.error(error);
    }
  };
  return (
    <>
      <Button className="reply-button" onClick={() => setShowModal(true)}>
        <FontAwesomeIcon icon={'pencil'}></FontAwesomeIcon>
        <span className="d-none d-md-inline">{'New Post'}</span>
      </Button>
      <Modal isOpen={showModal} toggle={() => setShowModal(false)}>
        <ModalHeader toggle={() => setShowModal(false)}>What's on your mind?</ModalHeader>
        <ModalBody>
          <Form onSubmit={handleSubmit}>
            <FormGroup>
              <Label for="content"></Label>
              <Input type="textarea" name="content" id="content" value={content} onChange={e => setContent(e.target.value)} />
            </FormGroup>
            <Button type="submit">Submit</Button>
          </Form>
        </ModalBody>
      </Modal>
    </>
  );
};

export default PostButton;
