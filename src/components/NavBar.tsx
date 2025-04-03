
import React from 'react';
import { Bell, User, Settings } from 'lucide-react';
import { Link, useNavigate } from 'react-router-dom';
import Logo from './Logo';
import { Button } from '@/components/ui/button';
import { 
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuTrigger 
} from '@/components/ui/dropdown-menu';

const NavBar: React.FC = () => {
  const navigate = useNavigate();
  
  return (
    <header className="fixed top-0 left-0 right-0 z-50 bg-white/80 backdrop-blur-md shadow-sm">
      <div className="container mx-auto px-4 py-3 flex justify-between items-center">
        <div className="flex items-center gap-6">
          <Link to="/">
            <Logo />
          </Link>
          
          <nav className="hidden md:flex gap-4">
            <Link to="/" className="text-vagacerta-blue hover:text-vagacerta-lightBlue font-medium">
              Home
            </Link>
            <Link to="/concursos" className="text-vagacerta-blue hover:text-vagacerta-lightBlue font-medium">
              Concursos
            </Link>
            <Link to="/cadconcursos" className="text-vagacerta-blue hover:text-vagacerta-lightBlue font-medium">
              Cadastrar Concursos
            </Link>
            <Link to="/simulados" className="text-vagacerta-blue hover:text-vagacerta-lightBlue font-medium">
              Simulados
            </Link>
            <Link to="/cronogramas" className="text-vagacerta-blue hover:text-vagacerta-lightBlue font-medium">
              Cronogramas
            </Link>
            <Link to="/biblioteca" className="text-vagacerta-blue hover:text-vagacerta-lightBlue font-medium">
              Biblioteca
            </Link>
            <Link to="/cadlivros" className="text-vagacerta-blue hover:text-vagacerta-lightBlue font-medium">
              Cadastrar Livros
            </Link>
          </nav>
        </div>
        
        <div className="flex items-center gap-3">
          <DropdownMenu>
            <DropdownMenuTrigger asChild>
              <Button variant="ghost" size="icon" className="nav-icon-button">
                <User size={20} />
              </Button>
            </DropdownMenuTrigger>
            <DropdownMenuContent align="end">
              <DropdownMenuItem onClick={() => navigate('/perfil')}>Perfil</DropdownMenuItem>
              <DropdownMenuItem onClick={() => navigate('/meusconcursos')}>Meus Concursos</DropdownMenuItem>
              <DropdownMenuItem>Sair</DropdownMenuItem>
            </DropdownMenuContent>
          </DropdownMenu>
          
          <Button 
            variant="ghost" 
            size="icon" 
            className="nav-icon-button"
            onClick={() => navigate('/config')}
          >
            <Settings size={20} />
          </Button>
          
          <Button 
            variant="ghost" 
            size="icon" 
            className="nav-icon-button"
            onClick={() => navigate('/notificacao')}
          >
            <Bell size={20} />
          </Button>
        </div>
      </div>
    </header>
  );
};

export default NavBar;
