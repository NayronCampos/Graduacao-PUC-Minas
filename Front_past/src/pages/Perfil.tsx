
import React, { useState } from 'react';
import NavBar from '@/components/NavBar';
import { Avatar, AvatarFallback, AvatarImage } from '@/components/ui/avatar';
import { Input } from '@/components/ui/input';
import { Button } from '@/components/ui/button';
import { useToast } from '@/components/ui/use-toast';
import { Calendar as CalendarIcon } from 'lucide-react';

const Perfil = () => {
  const { toast } = useToast();
  const [formData, setFormData] = useState({
    nome: 'Usuário VagaCerta',
    email: 'usuario@vagacerta.com.br',
    dataNascimento: '1990-06-15',
    senha: '********',
    endereco: 'Av. Paulista, 1000 - São Paulo, SP',
    telefone: '(11) 98765-4321'
  });

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: value
    }));
  };

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    toast({
      title: "Perfil atualizado",
      description: "Suas informações foram atualizadas com sucesso.",
    });
  };

  return (
    <div className="min-h-screen bg-vagacerta-bg">
      <NavBar />
      
      <main className="container mx-auto px-4 pt-24 pb-12">
        <div className="max-w-md mx-auto bg-white rounded-lg shadow-md p-6">
          <div className="flex flex-col items-center mb-6">
            <Avatar className="w-24 h-24 mb-4">
              <AvatarImage src="" alt="Foto de perfil" />
              <AvatarFallback className="bg-vagacerta-blue text-white text-2xl">UV</AvatarFallback>
            </Avatar>
            <h1 className="text-2xl font-bold text-vagacerta-blue">Meu Perfil</h1>
          </div>
          
          <form onSubmit={handleSubmit} className="space-y-4">
            <div>
              <label htmlFor="nome" className="block text-sm font-medium text-gray-700 mb-1">
                Nome do usuário
              </label>
              <Input 
                id="nome"
                name="nome"
                value={formData.nome}
                onChange={handleChange}
                placeholder="Nome do usuário"
              />
            </div>
            
            <div>
              <label htmlFor="email" className="block text-sm font-medium text-gray-700 mb-1">
                Email
              </label>
              <Input 
                id="email"
                name="email"
                type="email"
                value={formData.email}
                onChange={handleChange}
                placeholder="Email"
              />
            </div>
            
            <div>
              <label htmlFor="dataNascimento" className="block text-sm font-medium text-gray-700 mb-1">
                Data de Nascimento
              </label>
              <div className="relative">
                <Input 
                  id="dataNascimento"
                  name="dataNascimento"
                  type="date"
                  value={formData.dataNascimento}
                  onChange={handleChange}
                  className="pr-10"
                />
                <CalendarIcon className="absolute right-3 top-1/2 transform -translate-y-1/2 h-5 w-5 text-gray-400" />
              </div>
            </div>
            
            <div>
              <label htmlFor="senha" className="block text-sm font-medium text-gray-700 mb-1">
                Senha
              </label>
              <Input 
                id="senha"
                name="senha"
                type="password"
                value={formData.senha}
                onChange={handleChange}
                placeholder="Senha"
              />
            </div>
            
            <div>
              <label htmlFor="endereco" className="block text-sm font-medium text-gray-700 mb-1">
                Endereço
              </label>
              <Input 
                id="endereco"
                name="endereco"
                value={formData.endereco}
                onChange={handleChange}
                placeholder="Endereço"
              />
            </div>
            
            <div>
              <label htmlFor="telefone" className="block text-sm font-medium text-gray-700 mb-1">
                Telefone
              </label>
              <Input 
                id="telefone"
                name="telefone"
                value={formData.telefone}
                onChange={handleChange}
                placeholder="Telefone"
              />
            </div>
            
            <Button 
              type="submit" 
              className="w-full bg-vagacerta-blue hover:bg-vagacerta-darkBlue"
            >
              ATUALIZAR
            </Button>
          </form>
        </div>
      </main>
      
      <footer className="mt-auto py-6 bg-white">
        <div className="container mx-auto px-4 text-center text-sm text-gray-500">
          <p>© 2023 VAGA CERTA - Todos os direitos reservados</p>
        </div>
      </footer>
    </div>
  );
};

export default Perfil;
